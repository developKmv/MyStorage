const btn = document.getElementById('open-modal');
const modal = document.getElementById('modal1');

const closeBtn= document.querySelector('.modal_close');

function modal_active(){
    /*
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", '/create', false ); // false for synchronous request
    xmlHttp.send( null );
    */
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


btn.onclick=modal_active;

