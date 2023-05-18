const newUserTab = document.getElementById("nav-new-user-tab")
const formNew = document.getElementById('new_userForm')
const newUsername = document.getElementById('newUserUsername')
const newPassword = document.getElementById('newUserPassword')
const newEmail = document.getElementById('newUserEmail')
const newRoles = document.getElementById('newUserRole')
let option = ''

newUserTab.addEventListener('click', () => {
    newUsername.value = ''
    newEmail.value = ''
    newPassword.value = ''
    newRoles.innerHTML = `
        <option value="${dbRoles[0].id}">${dbRoles[0].authorities}</option>
        <option value="${dbRoles[1].id}">${dbRoles[1].authorities}</option>
        `
    option = 'new_userPanel'
})


formNew.addEventListener('submit', (form) => {
        form.preventDefault()
        fetch(usersUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email: newEmail.value,
                username: newUsername.value,
                password: newPassword.value,
                roles: getUserRoles(document.getElementById('newUserRole'))
            })
        })
            .then(response => console.log(response))
            .then(form.target.reset())
            .then(refillAllUsers)
    }
)