function rentCar(carId) {
    const userId = prompt("Entrez votre ID utilisateur:");
    if (userId) {
        fetch('/rents/rent', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ carId: carId, userId: parseInt(userId) })
        })
        .then(response => {
            if (response.ok) {
                alert("Voiture louée avec succès!");
                window.location.reload();
            } else {
                alert("Erreur lors de la location.");
            }
        });
    }
}

function returnCar(carId) {
    fetch('/return', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ carId: carId })
    })
    .then(response => {
        if (response.ok) {
            alert("Voiture rendue avec succès!");
            window.location.reload();
        } else {
            alert("Erreur lors du retour.");
        }
    });
}

function voirLocations() {
    const userId = document.getElementById('userId').value;
    if (userId) {
        window.location.href = `/myrents?userId=${userId}`;
    }
}