const btn = document.getElementById('open-modal');
const modal = document.getElementById('modal1');
const allFields = document.querySelectorAll('.my_field');
const modal2 = document.getElementById('modal2')

const closeBtn = document.getElementById('modal_close');
const closeBtn2 = document.getElementById('modal2_close');
const saveButton = document.getElementById('save_button');
const deleteButton = document.getElementById('delete_button');

var currentValue = 'none';

function modal_active(){

    modal.classList.add('modal_active');

    closeBtn.addEventListener('click',modal_inactive);
    modal.addEventListener('click',modal_hide);

    function modal_inactive(){
        modal.classList.remove('modal_active');
        closeBtn.removeEventListener('click',modal_inactive);
        modal.removeEventListener('click',modal_hide);
    }
    
    function modal_hide(event){
        if(event.target === modal) modal_inactive();
    }

};

function modal2_active(event){

    currentValue = event.currentTarget.innerHTML.toString();

    modal2.classList.add('modal_active');

    closeBtn2.addEventListener('click',modal_inactive);
    modal2.addEventListener('click',modal_hide);

    function modal_inactive(){
        modal2.classList.remove('modal_active');
        closeBtn2.removeEventListener('click',modal_inactive);
        modal2.removeEventListener('click',modal_hide);
    }

    function modal_hide(event){
        if(event.target === modal2) modal_inactive();
    }

};

function save_storage(e){

    let arrParams = currentValue.replaceAll('\n',"").trim().replace(/\s/g,'').replaceAll('<td>','').replace(/<\/td>$/,'').split('</td>');
    console.log(arrParams);

    let id = arrParams[0];
    let name = arrParams[1];
    let extension = arrParams[2];
    let url = '/storage?id='+id + '&name=' + name + '&extension='+ extension;

    console.log('url: ' + url);

    if(e.target.innerHTML==='Save'){

        let a = document.createElement('a');
        a.href = url;
        a.click();
        a.remove();
    };

};

function delete_storage(e){

     let arrParams = currentValue.replaceAll('\n',"").trim().replace(/\s/g,'').replaceAll('<td>','').replace(/<\/td>$/,'').split('</td>');
     console.log(arrParams);

     let id = arrParams[0];
     let name = arrParams[1];
     let extension = arrParams[2];

     let url = '/storage?id='+id;

     console.log('url: ' + url);

     if(e.target.innerHTML==='Delete'){
        /*    let a = document.createElement('a');
            a.href = url;
            a.setAttribute('data-method','delete');
            a.click();
            a.remove();*/

            let response = fetch(url, {method: 'DELETE'})
            .then(res => location.href='/').then(res=>console.log(res));

            
        };
};

btn.onclick=modal_active;
allFields.forEach(e =>{e.onclick=modal2_active});
saveButton.onclick=save_storage;
deleteButton.onclick=delete_storage;
