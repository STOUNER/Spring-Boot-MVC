//Константы - начало заполнения
const URL = "http://localhost:8080/get-all-users";
const URL_DELETE = "http://localhost:8080/delete-user";
const URL_SAVE = "http://localhost:8080/save-update-user";
const URL_CURRENT_USER = "http://localhost:8080/current-session-user"
const table = document.getElementById("myTable");
const nav = document.getElementById("currentUser")
const userTab = document.getElementById("home-tab")
const USER_ROLE = [
    {roleId: 1, roleName: "ROLE_USER", authority: "ROLE_USER"},
];
const ADMIN_ROLE = [
    {roleId: 2, roleName: "ROLE_ADMIN", authority: "ROLE_ADMIN"},
];
const form = document.getElementById("myNewUser");
//Константы - конец заполнения.

//Инициализация загрузки пользователей.
fetch(URL, {method: "GET", headers: {"Content-Type": "application/json"}})
    .then((response) => response.json())
    .then((users) => {
        localStorage.setItem("users", JSON.stringify(users));
        mapUsers(users);
    });


//Универсальный слушатель действия delete/edit
table.addEventListener("click", (event) => {
    const {name, id} = event.target;
    if (id === 'delete') {
        userDelete(name);
    } else if (id === 'edit') {
        userEdit(name);
    }
});

//Сохранение изменения пользовательских данных.
function saveChanges(event) {
    event.preventDefault();
    const {id, name, lastName, email, age, password, roles} = event.target.elements;
    event.target.elements;
    const dataUserForm = {
        id: id.value,
        name: name.value,
        lastName: lastName.value,
        email: email.value,
        age: age.value,
        password: password.value,
        roleId: roles.value === "1" ? 1 : 2

    }
    console.log(dataUserForm)
    getSaveUser(dataUserForm);
}

async function getSaveUser(user) {
    const response = await fetch(URL_SAVE, {
        method: "POST",
        headers: {"content-type": "application/json"},
        body: JSON.stringify(user),
    });
    if (response.ok) {
        const elmtTable = document.getElementById('myTable');
        const tableRows = elmtTable.getElementsByTagName('tr');
        const rowCount = tableRows.length;
        for (let x = rowCount - 1; x >= 0; x--) {
            elmtTable.removeChild(tableRows[x]);
        }
        fetch(URL, {method: "GET", headers: {"Content-Type": "application/json"}})
            .then((response) => response.json())
            .then((users) => {
                localStorage.setItem("users", JSON.stringify(users));
                mapUsers(users);
            });
    }


}


//Переход в режим изменения пользовательских данных
function userEdit(UID) {
    const users = JSON.parse(localStorage.getItem("users"));
    const user = users.find((user) => user.id == UID);
    const modalInputs = document.querySelectorAll('.modalInput');
    const getForm = document.getElementById('EditUserForm');
    getForm.onsubmit = saveChanges;
    for (let input of modalInputs) {
        if (input.name === 'roles') {
            input.value = user.roles[0].roleId;
        } else {
            input.value = user[input.name]
        }
    }
}

//Удаление пользователя.
async function userDelete(UID) {
    const users = JSON.parse(localStorage.getItem("users"));
    const user = users.find((user) => user.id == UID);

    const response = await fetch(URL_DELETE, {
        method: "POST",
        headers: {"content-type": "application/json"},
        body: JSON.stringify(user),
    });
    if (response.ok) {
        const newUsers = users.filter((user) => user.id != UID)
        localStorage.setItem("users", JSON.stringify(newUsers))
        const elmtTable = document.getElementById('myTable');
        const tableRows = elmtTable.getElementsByTagName('tr');
        const rowCount = tableRows.length;
        for (let x = rowCount - 1; x >= 0; x--) {
            elmtTable.removeChild(tableRows[x]);
        }
        mapUsers(newUsers);
    }
}


//Получение списка пользователей
function mapUsers(users) {
    return users.forEach((user) => {
        const row = `<tr>  
                    <td class="table-primary">${user.id}</td>
                    <td class="table-primary">${user.name}</td>
                    <td class="table-primary">${user.lastName}</td>
                    <td class="table-primary">${user.age}</td>
                    <td class="table-primary">${user.email}</td>
                    <td class="table-primary">${user.roles[0].roleName}</td>
                    <td class="table-primary">
                        <button type="button" class="btn btn-primary" name="${user.id}" id="delete" >Delete</button>
                    </td>
                    <td class="table-primary">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#EditUser" id="edit" name="${user.id}" >Edit</button>
                        <!-- Modal -->
                    </td>

                </tr>`;

        table.innerHTML += row;
    });
}


//Создание нового пользователя.
form.addEventListener("submit", (event) => {
    event.preventDefault();
    const {name, lastName, email, age, password, roles} = event.target.elements;
    const dataUserForm = {
        name: name.value,
        lastName: lastName.value,
        email: email.value,
        age: age.value,
        password: password.value,
        roleId: roles.value === "1" ? 1 : 2
    };

    getSaveUser(dataUserForm)
    TriggerTabs();
});

//Получение текущего пользователя
fetch(URL_CURRENT_USER, {method: "GET", headers: {"Content-Type": "application/json"}})
    .then((response) => response.json())
    .then((currentUser) => {
        localStorage.setItem("currentUser", JSON.stringify(currentUser))
        getCurrentUser(currentUser);

    });

//Передача данных в NAV-информация о пользователи.
function getCurrentUser(currentUser) {
    return nav.innerText = currentUser.name + ":" + " " + currentUser.email;
}

function TriggerTabs() {
    const firstTab = new bootstrap.Tab(userTab)
    firstTab.show()
}

$('#buttonEdit').click(function () {
    $('#EditUser').modal('hide');
});