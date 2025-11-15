document.getElementById('createAccountForm').addEventListener('submit', function(event) {
    event.preventDefault(); 
    
    const form = event.target;
    const statusElement = document.getElementById('statusMessage');
    statusElement.textContent = ''; 
    
    
    const password = form.password.value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if (password !== confirmPassword) {
        statusElement.textContent = "Erro: As senhas não coincidem!";
        statusElement.style.color = '#d73d48'; 
        return;
    }

    statusElement.textContent = "Criando conta...";
    statusElement.style.color = '#f96d3f'; 

   
    const pacientData = {
        "name": form.name.value,
        "email": form.email.value,
        "password": password,
        "accountStatus": "ACTIVE" 
    };
    
   
    fetch('/pacients', { 
        method: 'POST',
        headers: {
            'Content-Type': 'application/json' 
        },
        body: JSON.stringify(pacientData)
    })
    .then(response => {
        if (response.ok) {
            return response.json(); 
        } else {
            return response.text().then(text => {
                throw new Error('Falha ao registrar. Status: ' + response.status + ' | Detalhe: ' + text.substring(0, 150));
            });
        }
    })
    .then(pacientObj => {
        statusElement.textContent = `SUCESSO! Conta de ${pacientObj.name} criada com ID ${pacientObj.id}.`;
        statusElement.style.color = '#5cb85c'; 
        form.reset(); 
    })
    .catch(error => {
        console.error("Erro no POST:", error);
        statusElement.textContent = "ERRO: " + error.message;
        statusElement.style.color = '#d73d48';
    });
});