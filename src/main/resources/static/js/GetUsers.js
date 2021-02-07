//Константы
const URL = "http://localhost:8080/user-list-json";
const URL_DELETE = "http://localhost:8080/user-post-json";
const URL_SAVE = "http://localhost:8080/user-save-json";
const table = document.getElementById("myTable");
const USER_ROLE = [
    {roleId: 1, roleName: "ROLE_USER", authority: "ROLE_USER"},
];
const ADMIN_ROLE = [
    {roleId: 2, roleName: "ROLE_ADMIN", authority: "ROLE_ADMIN"},
];
table.addEventListener("click", (event) => {
    const {name} = event.target;
    const users = JSON.parse(localStorage.getItem("users"));
    const user = users.find((user) => user.id == name);

    fetch(URL_DELETE, {
        method: "POST",
        headers: {"content-type": "application/json"},
        body: JSON.stringify(user),
    });
});
//Получение списка пользователей
function mapUsers(users) {
    return users.forEach((user) => {
        const row = `<tr>  
                    <td class="table-primary">${user.id}</td>
                    <td class="table-primary">${user.name}</td>
                    <td class="table-primary">${user.lastName}</td>
                    <td class="table-primary">${user.age}</td>
                    <td class="table-primary">
                        <button type="button" class="btn btn-primary" name="${user.id}">Delete</button>
                    </td>
                </tr>`;
        table.innerHTML += row;
    });
}

fetch(URL, {method: "GET", headers: {"Content-Type": "application/json"}})
    .then((response) => response.json())
    .then((users) => {
        localStorage.setItem("users", JSON.stringify(users));
        mapUsers(users);
    });
const form = document.getElementById("myNewUser");
form.addEventListener("submit", (event) => {
    event.preventDefault();
    const {name, lastName, email, age, password, roles} = event.target.elements;
    //Мммм, сяки маки
    const dataUserForm = {
        name: name.value,
        lastName: lastName.value,
        email: email.value,
        age: age.value,
        password: password.value,
        roles: roles.value === "1" ? USER_ROLE : ADMIN_ROLE,
    };
    fetch(URL_SAVE, {
        method: "POST",
        headers: {"content-type": "application/json"},
        body: JSON.stringify(dataUserForm),
    });
    const elmtTable = document.getElementById('myTable');
    const tableRows = elmtTable.getElementsByTagName('tr');
    const rowCount = tableRows.length;

    for (const x = rowCount - 1; x > 0; x--) {
        elmtTable.removeChild(tableRows[x]);
    }
    localStorage.clear();
    fetch(URL, {method: "GET", headers: {"Content-Type": "application/json"}})
        .then((response) => response.json())
        .then((users) => {
            localStorage.setItem("users", JSON.stringify(users));
            mapUsers(users);
        });

});
