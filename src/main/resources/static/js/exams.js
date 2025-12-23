const API_URL = 'https://medops-p8i0.onrender.com/exams'; 
//const API_URL = 'http://localhost:8080/exams'; 

let exams = [];

const examsList = document.getElementById('examsList');
const searchInput = document.getElementById('searchInput');

document.addEventListener('DOMContentLoaded', () => {
    fetchExams();
});

async function fetchExams() {
    try {
        examsList.innerHTML = '<div class="no-appointments">Carregando exames...</div>';
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error('Erro ao buscar exames');
        
        exams = await response.json();
        renderExams(exams);
    } catch (error) {
        console.error('Erro:', error);
        examsList.innerHTML = '<div class="no-appointments" style="color: red;">Erro ao conectar com o servidor.</div>';
    }
}

function renderExams(examsToRender) {
    if (!examsToRender || examsToRender.length === 0) {
        examsList.innerHTML = '<div class="no-appointments">Nenhum exame encontrado</div>';
        return;
    }

    examsList.innerHTML = '';
    
	examsToRender.forEach((exam) => {
	        const doctorName = exam.requestingDoctor ? exam.requestingDoctor.name : exam.externalDoctorName;
	        const statusLabel = exam.examStatus; 
	        const statusClass = `status-${statusLabel.toLowerCase()}`;

	        const examItem = document.createElement('div');
	        examItem.className = 'appointment-item'; 
	        
	        examItem.innerHTML = `
	            <div class="appointment-left">
	                <div class="appointment-date">${exam.examName}</div>
	                <div class="exam-protocol">Protocolo: ${exam.protocolNumber}</div>
	                <div class="appointment-doctor">Médico: ${doctorName}</div>
	            </div>
	            <div class="appointment-right" style="flex-direction: column; align-items: flex-end;">
	                <div class="appointment-time">${formatDate(exam.executionDate)}</div>
	                <div class="status-badge ${statusClass}">${statusLabel}</div>
	                
	                <div class="exam-actions" style="margin-top: 10px;">
	                    ${exam.examStatus === 'DISPONIVEL' ? 
	                        `<button class="btn-download" onclick="window.open('${exam.resultFilePath}')">Ver Laudo</button>` : 
	                        '' 
	                    }

	                    ${(exam.examStatus !== 'DISPONIVEL' && exam.examStatus !== 'CANCELADO') ? 
	                        `<button class="delete-btn" onclick="deleteExam(${exam.id})">Excluir</button>` : 
	                        '' 
	                    }
	                </div>
	            </div>
	        `;
	        examsList.appendChild(examItem);
	    });
}

async function deleteExam(id) {
    if (confirm('Deseja realmente excluir este agendamento?')) {
        try {
            const response = await fetch(`${API_URL}/${id}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                appointments = appointments.filter(appointment => appointment.id !== id);
                renderAppointments(appointments);
                alert('Exame excluído com sucesso!');
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
    return new Date(isoString).toLocaleDateString('pt-BR');
}

if (btnNovoExame) {
    btnNovoExame.addEventListener('click', () => {
        window.location.href = '/newexam'; 
    });
}
// Lógica de pesquisa simplificada para o PO
searchInput.addEventListener('input', (e) => {
    const term = e.target.value.toLowerCase();
    const filtered = exams.filter(ex => 
        ex.examName.toLowerCase().includes(term) || 
        (ex.externalDoctorName && ex.externalDoctorName.toLowerCase().includes(term))
    );
    renderExams(filtered);
});