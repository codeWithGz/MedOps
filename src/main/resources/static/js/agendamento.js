// State
let selectedData = {
    unidade: '',
    especialidade: '',
    medico: '',
    date: null,
    month: 8,
    year: 2025
};

// Initialize calendar on load
document.addEventListener('DOMContentLoaded', function() {
    updateCalendar();
    updateSummary();
});

// Toggle Menu
function toggleMenu() {
    alert('Menu aberto!');
}

// Toggle Dropdown
function toggleDropdown(type) {
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
function selectOption(type, value) {
    selectedData[type] = value;
    document.getElementById(type + '-selected').textContent = value;
    document.getElementById(type + '-dropdown').classList.remove('active');
    updateSummary();
}

// Close dropdowns when clicking outside
document.addEventListener('click', function(event) {
    if (!event.target.closest('.select-wrapper')) {
        document.querySelectorAll('.dropdown').forEach(d => {
            d.classList.remove('active');
        });
    }
});

// Calendar Functions
function updateCalendar() {
    const monthSelect = document.getElementById('month-select');
    const yearSelect = document.getElementById('year-select');
    
    selectedData.month = parseInt(monthSelect.value);
    selectedData.year = parseInt(yearSelect.value);
    
    const daysContainer = document.getElementById('calendar-days');
    daysContainer.innerHTML = '';
    
    const firstDay = new Date(selectedData.year, selectedData.month, 1).getDay();
    const daysInMonth = new Date(selectedData.year, selectedData.month + 1, 0).getDate();
    
    // Empty cells for days before month starts
    for (let i = 0; i < firstDay; i++) {
        const emptyDiv = document.createElement('div');
        emptyDiv.className = 'calendar-day empty';
        daysContainer.appendChild(emptyDiv);
    }
    
    // Days of the month
    for (let day = 1; day <= daysInMonth; day++) {
        const dayBtn = document.createElement('button');
        dayBtn.className = 'calendar-day';
        dayBtn.textContent = day;
        dayBtn.onclick = () => selectDate(day);
        
        // Highlight today (9th) for September 2025
        if (day === 9 && selectedData.month === 8 && selectedData.year === 2025) {
            dayBtn.classList.add('today');
        }
        
        // Highlight range (10-12) for September 2025
        if (day >= 10 && day <= 12 && selectedData.month === 8 && selectedData.year === 2025) {
            dayBtn.classList.add('in-range');
        }
        
        // Highlight selected date
        if (selectedData.date === day) {
            dayBtn.classList.add('selected');
        }
        
        daysContainer.appendChild(dayBtn);
    }
    
    // Add next month days to fill the grid
    const totalCells = firstDay + daysInMonth;
    const remainingCells = 7 - (totalCells % 7);
    
    if (remainingCells < 7) {
        for (let i = 1; i <= remainingCells; i++) {
            const nextMonthDay = document.createElement('button');
            nextMonthDay.className = 'calendar-day next-month';
            nextMonthDay.textContent = i;
            nextMonthDay.onclick = () => {
                changeMonth(1);
                setTimeout(() => selectDate(i), 100);
            };
            daysContainer.appendChild(nextMonthDay);
        }
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
        
        if (selectedData.unidade) {
            html += `<div class="summary-item"><strong>Unidade:</strong> ${selectedData.unidade}</div>`;
        }
        
        if (selectedData.especialidade) {
            html += `<div class="summary-item"><strong>Especialidade:</strong> ${selectedData.especialidade}</div>`;
        }
        
        if (selectedData.medico) {
            html += `<div class="summary-item"><strong>Médico:</strong> ${selectedData.medico}</div>`;
        }
        
        if (selectedData.date) {
            html += `<div class="summary-item"><strong>Data:</strong> ${selectedData.date} de ${monthNames[selectedData.month]} de ${selectedData.year}</div>`;
        }
        
        const hour = document.getElementById('hour-input').value;
        const minute = document.getElementById('minute-input').value;
        html += `<div class="summary-item"><strong>Horário:</strong> ${hour}:${minute}</div>`;
        
        summaryContent.innerHTML = html;
    } else {
        summary.style.display = 'none';
    }
}

// Update summary when time changes
document.getElementById('hour-input').addEventListener('input', updateSummary);
document.getElementById('minute-input').addEventListener('input', updateSummary);

// Format time inputs
document.getElementById('hour-input').addEventListener('blur', function() {
    let value = parseInt(this.value);
    if (isNaN(value) || value < 0) value = 0;
    if (value > 23) value = 23;
    this.value = value.toString().padStart(2, '0');
});

document.getElementById('minute-input').addEventListener('blur', function() {
    let value = parseInt(this.value);
    if (isNaN(value) || value < 0) value = 0;
    if (value > 59) value = 59;
    this.value = value.toString().padStart(2, '0');
});
