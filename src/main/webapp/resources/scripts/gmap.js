function geocode(idForm, widgetVarMap) {
    let idInput = 'gmap:' + idForm + ':address';
    let address = document.getElementById(idInput).value;
    PF(widgetVarMap).geocode(address);
}

function geocode2(idForm, widgetVarMap) {
    let idInput = 'gmap:' + idForm + ':address2';
    let department = PF('geocode-department2').getSelectedValue();
    let province = PF('geocode-province2').getSelectedValue();
    let district = PF('geocode-district2').getSelectedValue();
    let address = document.getElementById(idInput).value;
    let addressToSearch = department + ' ' + province + ' ' + district + ' ' + address;
    console.log(addressToSearch);
    PF(widgetVarMap).geocode(addressToSearch.trim());
}

function reverseGeocode() {
    let lat = document.getElementById('lat').value,
        lng = document.getElementById('lng').value;
    console.log(lat, lng);
    PF('g_map').reverseGeocode(lat, lng);
}