const currentUserUrl = "http://localhost:8087/user"
const tabelBody = document.querySelector('tbody')
let title = document.getElementById('nav-header')
let adminTab = document.getElementById('admin-only-tab')

function fillUserData(user) {
    let tempHtmlText = ''
    const rolesNames = user.roles.map(role => role.authorities.replace("ROLE_", "")).join(', ');
    tempHtmlText += `<tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>${rolesNames}</td>
                     </tr>`;
    tabelBody.innerHTML = tempHtmlText;
    filHeader(user)
}

function filHeader(user) {
    const rolesNames = user.roles.map(role => role.authorities.replace("ROLE_", "")).join(', ');
    title.innerHTML = (user.email + ' with roles: ' + rolesNames)
    if (!rolesNames.includes('ADMIN')) {
        adminTab.style.display = 'none'
    }
}

fetch(currentUserUrl, {
    method: "GET",
    headers: {"Content-Type": "application/json"},
})
    .then(response => response.json())
    .then(data => {
        fillUserData(data)


    })