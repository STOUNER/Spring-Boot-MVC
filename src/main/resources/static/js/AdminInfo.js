const userTable = document.getElementById("myUserTable");
const URL_CURRENT_USER = "http://localhost:8080/session-user-json"
const nav = document.getElementById("currentUser")



function getDataInUserTable(currentUser) {
    const row = `<tr>  
                    <td class="table-primary">${currentUser.id}</td>
                    <td class="table-primary">${currentUser.name}</td>
                    <td class="table-primary">${currentUser.lastName}</td>
                    <td class="table-primary">${currentUser.age}</td>
                    <td class="table-primary">${currentUser.email}</td>
                    <td class="table-primary">${currentUser.roles[0].roleName}</td>
                </tr>`;

    userTable.innerHTML += row;
}


function getCurrentUser(currentUser) {
    console.log(nav)
    console.log(nav.innerText)
    return nav.innerText = currentUser.name + ":" + " " + currentUser.email;
}


fetch(URL_CURRENT_USER, {method: "GET", headers: {"Content-Type": "application/json"}})
    .then((response) => response.json())
    .then((currentUser) => {
        localStorage.setItem("currentUser", JSON.stringify(currentUser))
        getCurrentUser(currentUser);
        getDataInUserTable(currentUser)
    });