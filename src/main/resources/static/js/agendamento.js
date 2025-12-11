// State
let selectedData = {
    unidade: '',
    especialidade: '',
    medico: '',     // Nome do médico (para exibir)
    medicoId: null, // ID do médico (para enviar pro backend)
    date: null,
    month: new Date().getMonth(), // Mês atual
    year: new Date().getFullYear() // Ano atual
};

const API_URL = 'http://localhost:8080';

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
            selectedData.medicoId = extraData; // Salva o ID do médico
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

document.getElementById('hour-input').addEventListener('input', updateSummary);
document.getElementById('minute-input').addEventListener('input', updateSummary);

// =========================================================================
//  NOVA FUNÇÃO: ENVIAR PARA O BACKEND (DINÂMICA)
// =========================================================================

async function confirmarAgendamento() {
    // 1. RECUPERAR O ID DO USUÁRIO LOGADO
    const usuarioLogadoId = localStorage.getItem('usuarioId');

    // Se não tiver ID salvo, significa que não está logado
    if (!usuarioLogadoId) {
        alert("Você precisa estar logado para agendar!");
        window.location.href = "/login.html"; // Redireciona para login
        return;
    }

    // 2. Validação básica do formulário
    if (!selectedData.medicoId) {
        alert("Por favor, selecione um médico.");
        return;
    }
    if (!selectedData.date) {
        alert("Por favor, selecione uma data.");
        return;
    }

    // 3. Pegar valores dos inputs de hora e motivo
    const hour = document.getElementById('hour-input').value;
    const minute = document.getElementById('minute-input').value;
    
    const motivoInput = document.getElementById('motivo-input');
    const motiveValue = motivoInput ? motivoInput.value : "Consulta Agendada pelo Site"; 

    // 4. Formatar a Data para ISO-8601 (yyyy-MM-ddTHH:mm:ssZ)
    const pad = (n) => n < 10 ? '0' + n : n;
    
    const year = selectedData.year;
    const month = pad(selectedData.month + 1); 
    const day = pad(selectedData.date);
    
    const momentFormatted = `${year}-${month}-${day}T${hour}:${minute}:00Z`;

    // 5. Montar o JSON (Payload) com o ID REAL
    const appointmentData = {
        moment: momentFormatted,
        motive: motiveValue,
        doctor: {
            id: selectedData.medicoId
        },
        pacient: {
            id: parseInt(usuarioLogadoId) // <--- AQUI ESTÁ A MUDANÇA (Converte string para número)
        }
    };

    console.log("Enviando dados do usuário " + usuarioLogadoId, appointmentData);

    // 6. Enviar fetch
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
            // window.location.href = '/meus-agendamentos.html'; 
        } else {
            const errorText = await response.text();
            alert("Erro ao agendar: " + errorText);
        }
    } catch (error) {
        console.error("Erro na requisição:", error);
        alert("Erro de conexão com o servidor.");
    }
}