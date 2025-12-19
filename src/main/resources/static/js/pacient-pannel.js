const menuButton = document.getElementById('menuButton');
const sidebar = document.getElementById('sidebar');
const overlay = document.getElementById('overlay');
const cards = document.querySelectorAll('.card');
const userGreet = document.getElementById('userGreeting');
const username = localStorage.getItem('usuarioNome');

function toggleMenu() {
    sidebar.classList.toggle('open');
    overlay.classList.toggle('active');
}

menuButton.addEventListener('click', toggleMenu);

overlay.addEventListener('click', toggleMenu);

document.addEventListener('keydown', function(event) {
    if (event.key === 'Escape' && sidebar.classList.contains('open')) {
        toggleMenu();
    }
});

if (username) {
       userGreet.textContent += username;
   }

cards[0]?.classList.add('active');

cards.forEach(card => {
    card.addEventListener('mouseenter', function() {
        cards.forEach(c => c.classList.remove('active'));
        this.classList.add('active');
    });
});