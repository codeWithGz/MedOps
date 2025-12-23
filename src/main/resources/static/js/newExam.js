// --- CONFIGURAÇÃO E ESTADO ---
const API_URL = 'https://medops-p8i0.onrender.com';
//const API_URL = 'http://localhost:8080';

// Estado do calendário e seleção
let state = {
    month: new Date().getMonth(),
    year: new Date().getFullYear(),
    selectedDay: null,
    selectedExam: null,
    selectedDoctorId: null // ID numérico ou 'outro'
};

document.addEventListener('DOMContentLoaded', () => {
    // Define os selects para o mês/ano atual
    document.getElementById('month-select').value = state.month;
    document.getElementById('year-select').value = state.year;

    fetchDoctors();
    updateCalendar(); 
    updateSummary();

    // Listeners de Horário
    const hourInput = document.getElementById('hour-input');
    const minuteInput = document.getElementById('minute-input');
    hourInput.addEventListener('change', handleTimeChange);
    minuteInput.addEventListener('change', handleTimeChange);

    // Fechar dropdowns ao clicar fora
    window.onclick = (event) => {
        if (!event.target.matches('.select-btn') && !event.target.closest('.select-btn')) {
            document.querySelectorAll('.dropdown').forEach(d => d.classList.remove('active'));
        }
    };
});

// --- LÓGICA DO CALENDÁRIO ---

function updateCalendar() {
    const monthSelect = document.getElementById('month-select');
    const yearSelect = document.getElementById('year-select');
    
    state.month = parseInt(monthSelect.value);
    state.year = parseInt(yearSelect.value);
    
    const daysContainer = document.getElementById('calendar-days');
    daysContainer.innerHTML = '';
    
    const firstDay = new Date(state.year, state.month, 1).getDay(); 
    const daysInMonth = new Date(state.year, state.month + 1, 0).getDate();
    
    for (let i = 0; i < firstDay; i++) {
        const emptyDiv = document.createElement('div');
        emptyDiv.className = 'calendar-day empty';
        daysContainer.appendChild(emptyDiv);
    }
    
    for (let day = 1; day <= daysInMonth; day++) {
        const dayBtn = document.createElement('button');
        dayBtn.className = 'calendar-day';
        dayBtn.textContent = day;
        dayBtn.onclick = () => selectDate(day);
        
        if (state.selectedDay === day) {
            dayBtn.classList.add('selected');
        }
        
        daysContainer.appendChild(dayBtn);
    }
}

function selectDate(day) {
    state.selectedDay = day;
    updateCalendar(); 
    updateSummary();
}

function changeMonth(direction) {
    const monthSelect = document.getElementById('month-select');
    const yearSelect = document.getElementById('year-select');
    
    let newMonth = state.month + direction;
    let newYear = state.year;
    
    if (newMonth < 0) {
        newMonth = 11;
        newYear--;
    } else if (newMonth > 11) {
        newMonth = 0;
        newYear++;
    }
    
    monthSelect.value = newMonth;
    yearSelect.value = newYear;
    state.month = newMonth;
    state.year = newYear;
    
    updateCalendar();
}

// --- VALIDAÇÃO DE HORÁRIO ---

const padTime = (val) => val.toString().padStart(2, '0');

function handleTimeChange() {
    const hourInput = document.getElementById('hour-input');
    const minuteInput = document.getElementById('minute-input');
    
    let h = parseInt(hourInput.value);
    let m = parseInt(minuteInput.value);

    if (m >= 60) { m = 0; h++; }
    else if (m < 0) { m = 30; h--; }
    else if (m !== 0 && m !== 30) { m = m > 15 ? 30 : 0; }
    
    if (h < 8) { h = 8; m = 0; }
    if (h > 17 || (h === 17 && m > 30)) { h = 17; m = 30; }

    hourInput.value = padTime(h);
    minuteInput.value = padTime(m);

    updateSummary();
}

// --- INTERFACE E DROPDOWNS ---

function toggleDropdown(type) {
    const dropdown = document.getElementById(`${type}-dropdown`);
    document.querySelectorAll('.dropdown').forEach(d => {
        if (d !== dropdown) d.classList.remove('active');
    });
    dropdown.classList.toggle('active');
}

function selectOption(type, value, text, id = null) {
    const display = document.getElementById(`${type}-selected`);
    display.textContent = text || value;
    
    if (type === 'exam') {
        state.selectedExam = value;
    } else if (type === 'medico') {
        state.selectedDoctorId = id;
        handleExternalDoctorField(id === 'outro');
    }
    
    document.getElementById(`${type}-dropdown`).classList.remove('active');
    updateSummary();
}

function handleExternalDoctorField(isExternal) {
    let externalField = document.getElementById('external-doctor-group');
    
    if (isExternal && !externalField) {
        const html = `
            <div id="external-doctor-group" class="field-group" style="margin-top: 16px;">
                <h2 class="field-title" style="font-size: 24px;">Nome do Médico Externo</h2>
                <input type="text" id="external-doctor-name" class="select-btn" style="background: white; border: 1px solid #d9d9d9; cursor: text;" placeholder="Digite o nome completo">
            </div>
        `;
        document.querySelector('.left-column').insertAdjacentHTML('beforeend', html);
        document.getElementById('external-doctor-name').addEventListener('input', updateSummary);
    } else if (!isExternal && externalField) {
        externalField.remove();
    }
}

// --- API: CARREGAR MÉDICOS ---

async function fetchDoctors() {
    const dropdown = document.getElementById('medico-dropdown');
    try {
        const response = await fetch(`${API_URL}/doctors`); 
        const doctors = await response.json();
        
        let html = doctors.map(doc => `
            <button class="dropdown-item" onclick="selectOption('medico', '${doc.name}', '${doc.name}', ${doc.id})">
                ${doc.name} - ${doc.specialty}
            </button>
        `).join('');
        
        html += `<button class="dropdown-item" onclick="selectOption('medico', 'outro', 'Outro (Não listado)', 'outro')" style="color: #D75B5B; font-weight: bold; border-top: 1px solid #eee;">+ Outro Médico</button>`;
        
        dropdown.innerHTML = html;
    } catch (error) {
        console.error("Erro doctors:", error);
        dropdown.innerHTML = '<div class="dropdown-item">Erro ao carregar médicos</div>';
        dropdown.innerHTML += `<button class="dropdown-item" onclick="selectOption('medico', 'outro', 'Outro (Não listado)', 'outro')">+ Outro Médico</button>`;
    }
}

// --- RESUMO E ENVIO ---

function updateSummary() {
    const summary = document.getElementById('summary');
    const content = document.getElementById('summary-content');
    const hour = document.getElementById('hour-input').value;
    const minute = document.getElementById('minute-input').value;

    const hasDoctor = state.selectedDoctorId !== null;
    const hasExam = state.selectedExam !== null;
    const hasDate = state.selectedDay !== null;

    if (hasDoctor && hasExam && hasDate) {
        summary.style.display = 'block';
        
        const medicoTxt = state.selectedDoctorId === 'outro' ? 
            (document.getElementById('external-doctor-name')?.value || "Não informado") : 
            document.getElementById('medico-selected').textContent;
        
        const monthNames = ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'];
        
        content.innerHTML = `
            <div class="summary-item"><strong>Exame:</strong> ${state.selectedExam}</div>
            <div class="summary-item"><strong>Médico:</strong> ${medicoTxt}</div>
            <div class="summary-item"><strong>Data:</strong> ${state.selectedDay}/${monthNames[state.month]}/${state.year} às ${hour}:${minute}</div>
        `;
    }
}

async function confirmarAgendamento() {
    const usuarioLogadoId = localStorage.getItem('usuarioId');

    if (!usuarioLogadoId) {
        alert("Você precisa estar logado para agendar!");
        window.location.href = "/login.html"; 
        return;
    }

    if (!state.selectedDay || !state.selectedExam || !state.selectedDoctorId) {
        alert("Preencha todos os campos obrigatórios (Exame, Médico e Data).");
        return;
    }

    const hour = document.getElementById('hour-input').value;
    const minute = document.getElementById('minute-input').value;
    
    const dateStr = `${state.year}-${padTime(state.month + 1)}-${padTime(state.selectedDay)}T${hour}:${minute}:00Z`;

    const isExternal = state.selectedDoctorId === 'outro';
    const externalName = isExternal ? document.getElementById('external-doctor-name').value : null;

    if (isExternal && !externalName) {
        alert("Por favor, digite o nome do médico externo.");
        return;
    }

    const payload = {
        examName: state.selectedExam,
        executionDate: dateStr,
        resultReleaseDate: null,
        resultFilePath: null,
        examStatus: "AGENDADO",
        requestingDoctor: isExternal ? null : { id: parseInt(state.selectedDoctorId) }, 
        pacient: { 
            id: parseInt(usuarioLogadoId) 
        },
        preparationInstructions: "Nenhum preparo necessario",
        externalDoctorName: externalName
    };

    console.log("JSON Enviado:", JSON.stringify(payload, null, 2));

    try {
        const response = await fetch(`${API_URL}/exams`, { 
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json' 
            },
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            alert("Agendamento realizado com sucesso!");
            window.location.href = "/myexams";
        } else {

            const errorText = await response.text();
            console.error("Erro Backend:", errorText);

            try {
                const errorJson = JSON.parse(errorText);
                alert(`Erro ao agendar: ${errorJson.message || errorJson.error || "Erro desconhecido"}`);
            } catch (e) {
                alert(`Erro ao agendar: ${errorText}`);
            }
        }
    } catch (error) {
        console.error("Erro de Rede:", error);
        alert("Erro de conexão com o servidor. Verifique se o backend está rodando.");
    }
}

