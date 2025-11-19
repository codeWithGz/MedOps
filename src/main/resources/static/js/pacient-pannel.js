// Elementos do DOM
const menuButton = document.getElementById('menuButton');
const sidebar = document.getElementById('sidebar');
const overlay = document.getElementById('overlay');
const cards = document.querySelectorAll('.card');

// Função para alternar o menu
function toggleMenu() {
    sidebar.classList.toggle('open');
    overlay.classList.toggle('active');
}

// Event listener para o botão do menu
menuButton.addEventListener('click', toggleMenu);

// Event listener para o overlay (fecha o menu ao clicar fora)
overlay.addEventListener('click', toggleMenu);

// Fecha o menu ao pressionar a tecla ESC
document.addEventListener('keydown', function(event) {
    if (event.key === 'Escape' && sidebar.classList.contains('open')) {
        toggleMenu();
    }
});

// Gerenciamento dos cards (expandir/colapsar)
// Define o primeiro card como ativo inicialmente
cards[0]?.classList.add('active');

// Adiciona event listeners para cada card
cards.forEach(card => {
    card.addEventListener('mouseenter', function() {
        // Remove a classe 'active' de todos os cards
        cards.forEach(c => c.classList.remove('active'));
        // Adiciona a classe 'active' apenas ao card atual
        this.classList.add('active');
    });
});