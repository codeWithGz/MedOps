// State
let selectedData = {
    unidade: '',
    especialidade: '',
    medico: '',     
    medicoId: null, 
    date: null,
    month: new Date().getMonth(), 
    year: new Date().getFullYear() 
};

const API_URL = 'https://medops-p8i0.onrender.com';
//const API_URL = 'http://localhost:8080';

// Initialize calendar on load
document.addEventListener('DOMContentLoaded', function() {
    updateCalendar();
    updateSummary();
});

// Toggle Menu
function toggleMenu() {
    console.log('Menu clicado');
}

// Toggle Dropdown
function toggleDropdown(type) {
    if (type === 'medico' && !selectedData.especialidade) {
        alert('Por favor, selecione uma especialidade primeiro.');
        return;
    }

    const dropdown = document.getElementById(type + '-dropdown');
    const allDropdowns = document.querySelectorAll('.dropdown');
    
    allDropdowns.forEach(d => {
        if (d !== dropdown) {
            d.classList.remove('active');
        }
    });
    
    dropdown.classList.toggle('active');
}

// Select Option
function selectOption(type, value, extraData = null) {
    selectedData[type] = value;
    document.getElementById(type + '-selected').textContent = value;
    document.getElementById(type + '-dropdown').classList.remove('active');

    // Lógica específica
    if (type === 'especialidade') {
        resetMedicoSelection();
        fetchDoctorsBySpecialty(value);
    } else if (type === 'medico') {
        if (extraData) {
            selectedData.medicoId = extraData; 
        }
    }

    updateSummary();
}

// Resetar seleção de médico
function resetMedicoSelection() {
    selectedData.medico = '';
    selectedData.medicoId = null;
    document.getElementById('medico-selected').textContent = 'Selecione';
    const medicoDropdown = document.getElementById('medico-dropdown');
    medicoDropdown.innerHTML = ''; 
}

// Buscar médicos na API
async function fetchDoctorsBySpecialty(specialtyName) {
    const dropdown = document.getElementById('medico-dropdown');
    dropdown.innerHTML = '<div class="dropdown-item" style="cursor: default">Carregando...</div>';

    try {
        const response = await fetch(`${API_URL}/doctors/specialty/${specialtyName}`);
        
        if (!response.ok) throw new Error('Erro na resposta da API');

        const doctors = await response.json();
        dropdown.innerHTML = '';

        if (doctors.length === 0) {
            dropdown.innerHTML = '<div class="dropdown-item" style="cursor: default">Nenhum médico encontrado</div>';
            return;
        }

        doctors.forEach(doc => {
            const btn = document.createElement('button');
            btn.className = 'dropdown-item';
            btn.textContent = doc.name;
            // Passa o ID como terceiro parâmetro
            btn.onclick = () => selectOption('medico', doc.name, doc.id);
            dropdown.appendChild(btn);
        });

    } catch (error) {
        console.error('Erro ao buscar médicos:', error);
        dropdown.innerHTML = '<div class="dropdown-item" style="color: red">Erro ao carregar lista</div>';
    }
}

// Close dropdowns when clicking outside
document.addEventListener('click', function(event) {
    if (!event.target.closest('.select-wrapper')) {
        document.querySelectorAll('.dropdown').forEach(d => {
            d.classList.remove('active');
        });
    }
});

// --- Lógica do Calendário ---
function updateCalendar() {
    const monthSelect = document.getElementById('month-select');
    const yearSelect = document.getElementById('year-select');
    
    if(!monthSelect || !yearSelect) return;

    selectedData.month = parseInt(monthSelect.value);
    selectedData.year = parseInt(yearSelect.value);
    
    const daysContainer = document.getElementById('calendar-days');
    daysContainer.innerHTML = '';
    
    const firstDay = new Date(selectedData.year, selectedData.month, 1).getDay();
    const daysInMonth = new Date(selectedData.year, selectedData.month + 1, 0).getDate();
    
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
        
        if (selectedData.date === day) {
            dayBtn.classList.add('selected');
        }
        
        daysContainer.appendChild(dayBtn);
    }
}

function selectDate(day) {
    selectedData.date = day;
    updateCalendar();
    updateSummary();
}

function changeMonth(direction) {
    const monthSelect = document.getElementById('month-select');
    const yearSelect = document.getElementById('year-select');
    
    let newMonth = selectedData.month + direction;
    let newYear = selectedData.year;
    
    if (newMonth < 0) {
        newMonth = 11;
        newYear--;
    } else if (newMonth > 11) {
        newMonth = 0;
        newYear++;
    }
    
    selectedData.month = newMonth;
    selectedData.year = newYear;
    
    monthSelect.value = newMonth;
    yearSelect.value = newYear;
    
    updateCalendar();
}

// Update Summary
function updateSummary() {
    const summary = document.getElementById('summary');
    const summaryContent = document.getElementById('summary-content');
    
    const hasData = selectedData.unidade || selectedData.especialidade || 
                    selectedData.medico || selectedData.date;
    
    if (hasData) {
        summary.style.display = 'block';
        
        const monthNames = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
                           'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];
        
        let html = '';
        
        if (selectedData.unidade) html += `<div class="summary-item"><strong>Unidade:</strong> ${selectedData.unidade}</div>`;
        if (selectedData.especialidade) html += `<div class="summary-item"><strong>Especialidade:</strong> ${selectedData.especialidade}</div>`;
        if (selectedData.medico) html += `<div class="summary-item"><strong>Médico:</strong> ${selectedData.medico}</div>`;
        if (selectedData.date) html += `<div class="summary-item"><strong>Data:</strong> ${selectedData.date} de ${monthNames[selectedData.month]} de ${selectedData.year}</div>`;
        
        const hour = document.getElementById('hour-input').value;
        const minute = document.getElementById('minute-input').value;
        html += `<div class="summary-item"><strong>Horário:</strong> ${hour}:${minute}</div>`;
        
        summaryContent.innerHTML = html;
    } else {
        summary.style.display = 'none';
    }
}

// --- Lógica de Controle de Horário (08:00 - 17:30) ---
const hourInput = document.getElementById('hour-input');
const minuteInput = document.getElementById('minute-input');

const padTime = (val) => val.toString().padStart(2, '0');

function handleTimeChange() {
    let h = parseInt(hourInput.value);
    let m = parseInt(minuteInput.value);

    // 1. Lógica de Rollover (virada de hora pelos minutos)
    if (m >= 60) {
        m = 0;
        h++;
    } else if (m < 0) {
        m = 30;
        h--;
    } else if (m !== 0 && m !== 30) {
        // Arredonda valores digitados manualmente (ex: 15 vira 30)
        m = m > 15 ? 30 : 0;
    }

    // 2. Validação de Limites (08:00 até 17:30)

    // Limite Mínimo: Se for antes das 08:00, força 08:00
    if (h < 8) {
        h = 8;
        m = 0;
    }

    // Limite Máximo: Se passar de 17:30, força 17:30
    // (Se hora > 17 OU se for 17h e minutos > 30)
    if (h > 17 || (h === 17 && m > 30)) {
        h = 17;
        m = 30;
    }

    // 3. Aplica os valores corrigidos
    hourInput.value = padTime(h);
    minuteInput.value = padTime(m);

    updateSummary();
}

// Listeners
minuteInput.addEventListener('change', handleTimeChange);
hourInput.addEventListener('change', handleTimeChange);

// Inicializa correto ao carregar
handleTimeChange();



async function confirmarAgendamento() {

    const usuarioLogadoId = localStorage.getItem('usuarioId');

    if (!usuarioLogadoId) {
        alert("Você precisa estar logado para agendar!");
        window.location.href = "/login.html"; 
        return;
    }

    if (!selectedData.medicoId) {
        alert("Por favor, selecione um médico.");
        return;
    }
    if (!selectedData.date) {
        alert("Por favor, selecione uma data.");
        return;
    }

    const hour = document.getElementById('hour-input').value;
    const minute = document.getElementById('minute-input').value;
    
    const motivoInput = document.getElementById('motivo-input');
    const motiveValue = motivoInput ? motivoInput.value : "Consulta Agendada pelo Site"; 


    const pad = (n) => n < 10 ? '0' + n : n;
    
    const year = selectedData.year;
    const month = pad(selectedData.month + 1); 
    const day = pad(selectedData.date);
    
    const momentFormatted = `${year}-${month}-${day}T${hour}:${minute}:00Z`;


    const appointmentData = {
        moment: momentFormatted,
        motive: motiveValue,
        doctor: {
            id: selectedData.medicoId
        },
        pacient: {
            id: parseInt(usuarioLogadoId) 
        }
    };

    console.log("Enviando dados do usuário " + usuarioLogadoId, appointmentData);


    try {
        const response = await fetch(`${API_URL}/consultations`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(appointmentData)
        });

        if (response.ok) {
            alert("Consulta agendada com sucesso!");
           
        } else {
            const errorText = await response.text();
            alert( selectedData.medico + ": " + errorText);
        }
    } catch (error) {
        console.error("Erro na requisição:", error);
        alert("Erro de conexão com o servidor.");
    }
}