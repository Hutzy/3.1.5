const usersUrl = "http://localhost:8080/admin/"
const userUrl = "http://localhost:8080/user/"

const title = document.getElementById('nav-header')
const userTable = document.querySelector('tbody')

const modalForm = document.getElementById('modalForm')
const deleteModalForm = document.getElementById('deleteModalForm')
const userModal = new bootstrap.Modal(document.getElementById('editTab'))
const userDeleteModal = new bootstrap.Modal(document.getElementById('deleteTab'))


const dbRoles = [{id: 1, authorities: "ROLE_ADMIN"}, {id: 2, authorities: "ROLE_USER"}]


/**
 *Заполнение таблицы данными
 */
function fillUserTable(users) {
    let tempHtmlText = '';

    users.forEach(user => {
        const rolesNames = user.roles.map(role => role.authorities.replace("ROLE_", "")).join(', ');
        tempHtmlText += `<tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>${rolesNames}</td>
                        <td class="text-center"><a class="editBtn btn btn-info" id="editBtn"> edit</a></td>
                        <td class="text-center"><a class="deleteBtn btn btn-danger" id="deleteBtn"> delete</a></td>
                     </tr>`;
    });
    userTable.innerHTML = tempHtmlText;
}


/**
 *Заполнение  верхеного навбара
 */

function filNavbar(user) {
    const rolesNames = user.roles.map(role => role.authorities.replace("ROLE_", "")).join(', ');
    title.innerHTML = (user.email + ' with roles: ' + rolesNames)
}


/**
 *Декларирование событий
 */
const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}

/**
 *Обновление данных в таблице
 */
const refillAllUsers = () => {
    fetch(usersUrl)
        .then(response => response.json())
        .then(data => {
            fillUserTable(data)
        })
}



/**
 *Запрос на получение всех пользователей
 */

fetch(usersUrl, {
    method: "GET",
    headers: {"Content-Type": "application/json"},
})
    .then(response => response.json())
    .then(data => {
        console.log(data)
        fillUserTable(data)
    })



/**
 *Запрос на получение текущего пользователя
 */
fetch(userUrl, {
    method: "GET",
    headers: {"Content-Type": "application/json"},
})
    .then(response => response.json())
    .then(data => {
        filNavbar(data)
    })

/**
 *Обработка выбора ролей
 */
let getUserRoles = (options) => {
    let array = []
    for (let i = 0; i < options.length; i++) {
        if (options[i].selected) {
            let role = {id: options[i].value, authorities: options[i].innerHTML}
            array.push(role)
        }
    }
    return array
}



/**
 *Модальное окно Edit
 */


const id = document.getElementById('userId')
const username = document.getElementById('username')
const email = document.getElementById('email')
const roles = document.getElementById('roles')
const password = document.getElementById('password')


on(document, 'click', '.editBtn', e => {
    const editNode = e.target.parentNode.parentNode
    id.value = editNode.children[0].innerHTML

    function fillForm(userData) {
        username.value = userData.username
        email.value = userData.email
        password.value = userData.password
        roles.innerHTML = `
            <option value="${dbRoles[0].id}">${dbRoles[0].authorities}</option>
            <option value="${dbRoles[1].id}">${dbRoles[1].authorities}</option>
            `
        Array.from(roles.options).forEach(e => {
            userData.roles.forEach(role => {
                if (e.text.indexOf(role.authorities) !== -1) {
                    e.selected = true
                }
            })
        })
        console.log(roles.options.selected)
    }

    fetch(usersUrl + '' + id.value,
        {method: "GET", headers: {"Content-Type": "application/json"}})
        .then(response => response.json())
        .then(data => fillForm(data))

    userModal.show()
})

modalForm.addEventListener('submit', (ev) => {
    ev.preventDefault()
    fetch(usersUrl + id.value, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: id.value,
            email: email.value,
            username: username.value,
            password: password.value,
            roles: getUserRoles(document.getElementById('roles'))
        })
    })
        .then(response => console.log(response))
        .then(refillAllUsers)
    userModal.hide()
})



/**
 *Модальное окно Delete
 */
const deleteUserId = document.getElementById('deleteUserId')
const deleteUsername = document.getElementById('deleteUsername')
const deleteEmail = document.getElementById('deleteEmail')
const deleteRoles = document.getElementById('deleteRoles')
const deletePassword = document.getElementById('deletePassword')


on(document, 'click', '.deleteBtn', e => {
    const editNode = e.target.parentNode.parentNode
    deleteUserId.value = editNode.children[0].innerHTML

    function fillForm(userData) {
        deleteUsername.value = userData.username
        deleteEmail.value = userData.email
        deletePassword.value = userData.password
        deleteRoles.innerHTML = `
            <option class="disabled" value="${dbRoles[0].id}">${dbRoles[0].authorities}</option>
            <option class="disabled" value="${dbRoles[1].id}">${dbRoles[1].authorities}</option>
            `
        Array.from(deleteRoles.options).forEach(e => {
            userData.roles.forEach(role => {
                if (e.text.indexOf(role.authorities) !== -1) {
                    e.selected = true
                }
                e.disabled = true;
            })
        })
        console.log(roles.options.selected)
    }

    fetch(usersUrl + '' + deleteUserId.value,
        {method: "GET", headers: {"Content-Type": "application/json"}})
        .then(response => response.json())
        .then(data => fillForm(data))

    userDeleteModal.show()
})

deleteModalForm.addEventListener('submit', (ev) => {
    ev.preventDefault()
    console.log(deleteUserId.value)
    fetch(usersUrl + '' + deleteUserId.value, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => console.log(response))
        .then(refillAllUsers)
    userDeleteModal.hide()
})