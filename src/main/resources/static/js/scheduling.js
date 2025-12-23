const API_URL = 'https://medops-p8i0.onrender.com/consultations'; 
//const API_URL = 'http://localhost:8080/consultations'; 
let appointments = [];

const btnNovoAgendamento = document.getElementById('btnNovoAgendamento');
const appointmentsList = document.getElementById('appointmentsList');
const searchInput = document.getElementById('searchInput');
const btnSearch = document.querySelector('.btn-search');
document.addEventListener('DOMContentLoaded', () => {
    fetchAppointments();
});


async function fetchAppointments() {
    try {
        appointmentsList.innerHTML = '<div class="loading">Carregando agendamentos...</div>';

        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error('Erro ao buscar dados');
        }

        const data = await response.json();
        
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

function formatDate(isoString) {
    if (!isoString) return '--/--/----';
    const date = new Date(isoString);
    
    return date.toLocaleDateString('pt-BR', { timeZone: 'UTC' });
}

function formatTime(isoString) {
    if (!isoString) return '--:--';
    const date = new Date(isoString);
    return date.toLocaleTimeString('pt-BR', { 
        hour: '2-digit', 
        minute: '2-digit',
        timeZone: 'UTC' 
    });
}

function renderAppointments(appointmentsToRender) {
    if (!appointmentsToRender || appointmentsToRender.length === 0) {
        appointmentsList.innerHTML = '<div class="no-appointments">Nenhum agendamento encontrado</div>';
        return;
    }

    appointmentsList.innerHTML = '';
    
    appointmentsToRender.forEach((appointment) => {
        const appointmentItem = document.createElement('div');
        appointmentItem.className = 'appointment-item';
        
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

if (searchInput) {
    searchInput.addEventListener('input', (e) => {
        const searchTerm = e.target.value.toLowerCase();
        
        if (searchTerm === '') {
            renderAppointments(appointments);
            return;
        }

        const filteredAppointments = appointments.filter(appointment => {
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

const menuIcon = document.querySelector('.menu-icon');
if (menuIcon) {
    menuIcon.addEventListener('click', () => {
        console.log('Menu clicado');
    });
}