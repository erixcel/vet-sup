import Swal from 'sweetalert2';

export class ShowAlert {

  constructor(){}

  save(text: string){
    Swal.fire({
      position: 'center',
      icon: 'success',
      title: `${text}`
    });
  }

  error(text: string){
    Swal.fire({
      position: 'center',
      icon: 'error',
      title: `${text}`
    });
  }

  question(textCuestion:string, onSuccess:() => void){
    Swal.fire({
        title: '¿ESTAS SEGURO?',
        text: `${textCuestion}`,
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Si, adelante',
        cancelButtonText: 'Cancelar',
        buttonsStyling: true,
    })
    .then((result) => {
      if (result.value) onSuccess()
    });
  }

  loading(){
    Swal.fire({
      allowOutsideClick: false,
      showConfirmButton: false,
      customClass: {
        loader: 'custom-loader',
        popup: 'custom-popup'
      },
      willOpen: () => {
        Swal.showLoading()
      }
    });
  }

  delete(cuestionWarning:string, onSuccess:() => void, MessageDelete:string, MessageCancel:string){
    Swal
      .fire({
        title: '¿ESTAS SEGURO?',
        text: `${cuestionWarning}`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Si , elimínalo',
        cancelButtonText: 'No, cancelar',
        buttonsStyling: true,
      })
      .then((result) => {
        if (result.value) {
          onSuccess()
          Swal.fire(
            `Eliminacion Exitosa`,
            `${MessageDelete}`,
            'success'
          );
        }
        else{
          Swal.fire(
            `Eliminacion Cancelada`,
            `${MessageCancel}`,
            'error'
          );
        }
      });
  }


}
