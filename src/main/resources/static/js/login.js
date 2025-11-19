document.getElementById('loginForm').addEventListener('submit', function(event) {
	event.preventDefault();

	const form = event.target;
	const statusElement = document.getElementById('statusMessage');

	statusElement.textContent = '';
	statusElement.style.color = '#f96d3f';

	const email = form.email.value;
	const password = form.password.value;

	if (!email || !password) {
		statusElement.textContent = "Por favor, preencha o Usuário (Email) e a Senha.";
		statusElement.style.color = '#d73d48';
		return;
	}

	statusElement.textContent = "Autenticando...";

	const credentials = {
		"email": email,
		"password": password
	};

	fetch('/auth/login', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(credentials)
	})
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				if (response.status === 401 || response.status === 403) {
					throw new Error('Credenciais inválidas. Verifique seu usuário e senha.');
				} else {
					throw new Error(`Erro no servidor (Status: ${response.status}). Tente novamente.`);
				}
			}
		})
		.then(authData => {
			statusElement.textContent = "SUCESSO! Você será redirecionado.";
			statusElement.style.color = '#5cb85c';

			window.location.href = '/pacientpannel';
		})
		.catch(error => {
			console.error("Erro no Login:", error);
			statusElement.textContent = "ERRO: " + error.message;
			statusElement.style.color = '#d73d48';
		});
});