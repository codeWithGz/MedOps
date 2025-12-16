// Configuração da API

const API_URL = 'https://medops-p8i0.onrender.com/consultations'; 
//const API_URL = 'http://localhost:8080/consultations'; 

// Estado da aplicação
let appointments = [];

// Elementos do DOM
const btnNovoAgendamento = document.getElementById('btnNovoAgendamento');
const appointmentsList = document.getElementById('appointmentsList');
const searchInput = document.getElementById('searchInput');
const btnSearch = document.querySelector('.btn-search');

// Inicialização
document.addEventListener('DOMContentLoaded', () => {
    fetchAppointments();
});

// --- FUNÇÕES DE API ---

async function fetchAppointments() {
    try {
        appointmentsList.innerHTML = '<div class="loading">Carregando agendamentos...</div>';

        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error('Erro ao buscar dados');
        }

        const data = await response.json();
        
        // A API retorna o JSON que você mostrou. Salvamos no estado global.
        appointments = data;
        renderAppointments(appointments);

    } catch (error) {
        console.error('Erro:', error);
        appointmentsList.innerHTML = '<div class="error">Erro ao carregar agendamentos. Tente novamente mais tarde.</div>';
    }
}

async function deleteAppointment(id) {
    if (confirm('Deseja realmente excluir este agendamento?')) {
        try {
            const response = await fetch(`${API_URL}/${id}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                appointments = appointments.filter(appointment => appointment.id !== id);
                renderAppointments(appointments);
                alert('Agendamento excluído com sucesso!');
            } else {
                alert('Erro ao excluir no servidor.');
            }
        } catch (error) {
            console.error('Erro ao excluir:', error);
            alert('Erro de conexão ao tentar excluir.');
        }
    }
}

// --- FUNÇÕES DE DATA E HORA ---

function formatDate(isoString) {
    if (!isoString) return '--/--/----';
    const date = new Date(isoString);
    
    // O parâmetro timeZone: 'UTC' força o navegador a não converter para o horário local
    return date.toLocaleDateString('pt-BR', { timeZone: 'UTC' });
}

function formatTime(isoString) {
    if (!isoString) return '--:--';
    const date = new Date(isoString);
    
    // O parâmetro timeZone: 'UTC' faz mostrar 10:00 se estiver escrito 10:00Z
    return date.toLocaleTimeString('pt-BR', { 
        hour: '2-digit', 
        minute: '2-digit',
        timeZone: 'UTC' 
    });
}

// --- RENDERIZAÇÃO ---

function renderAppointments(appointmentsToRender) {
    if (!appointmentsToRender || appointmentsToRender.length === 0) {
        appointmentsList.innerHTML = '<div class="no-appointments">Nenhum agendamento encontrado</div>';
        return;
    }

    appointmentsList.innerHTML = '';
    
    appointmentsToRender.forEach((appointment) => {
        const appointmentItem = document.createElement('div');
        appointmentItem.className = 'appointment-item';
        
        // AQUI ESTÃO AS MUDANÇAS PRINCIPAIS DE MAPEAMENTO:
        // appointment.moment -> usamos para data e hora
        // appointment.doctor.name -> nome do médico
        // appointment.motive -> motivo
        
        appointmentItem.innerHTML = `
            <div class="appointment-left">
                <div class="appointment-date">Data: ${formatDate(appointment.moment)}</div>
                <div class="appointment-doctor"><strong>Médico:</strong> ${appointment.doctor.name}</div>
                <div class="appointment-reason">Motivo: ${appointment.motive}</div>
            </div>
            <div class="appointment-right">
                <div class="appointment-time">Horário: ${formatTime(appointment.moment)}</div>
            </div>
            <button class="delete-btn" onclick="deleteAppointment(${appointment.id})">Excluir</button>
        `;
        appointmentsList.appendChild(appointmentItem);
    });
}

// --- EVENTOS ---

if (btnNovoAgendamento) {
    btnNovoAgendamento.addEventListener('click', () => {
        window.location.href = '/agendamento';
    });
}

if (btnSearch) {
    btnSearch.addEventListener('click', () => {
        searchInput.classList.toggle('active');
        if (searchInput.classList.contains('active')) {
            searchInput.focus();
        }
    });
}

// Busca atualizada para a nova estrutura do objeto
if (searchInput) {
    searchInput.addEventListener('input', (e) => {
        const searchTerm = e.target.value.toLowerCase();
        
        if (searchTerm === '') {
            renderAppointments(appointments);
            return;
        }

        const filteredAppointments = appointments.filter(appointment => {
            // Proteção com '?' (Optional Chaining) caso venha nulo
            const doctorName = appointment.doctor?.name?.toLowerCase() || '';
            const motive = appointment.motive?.toLowerCase() || '';
            const dateStr = formatDate(appointment.moment);
            
            return doctorName.includes(searchTerm) ||
                   motive.includes(searchTerm) ||
                   dateStr.includes(searchTerm);
        });

        renderAppointments(filteredAppointments);
    });
}

// Menu Mobile
const menuIcon = document.querySelector('.menu-icon');
if (menuIcon) {
    menuIcon.addEventListener('click', () => {
        console.log('Menu clicado');
    });
}