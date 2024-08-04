
export function convertFileToBase64(file: File, callback: (result: string) => void) {
  const reader = new FileReader();
  reader.onload = () => {
    callback(reader.result as string);
  };
  reader.readAsDataURL(file);
}

export function compressImage(file: File): Promise<File> {
  return new Promise((resolve, reject) => {
    const image = new Image();
    image.src = URL.createObjectURL(file);
    image.onload = () => {
      const canvas = document.createElement('canvas');
      canvas.width = 500;
      canvas.height = 500;
      const context = canvas.getContext('2d');
      let side = Math.min(image.width, image.height);
      let sx = (image.width - side) / 2;
      let sy = (image.height - side) / 2;
      context?.drawImage(image, sx, sy, side, side, 0, 0, 500, 500);
      let quality = 0.9;
      let compressedDataUrl = canvas.toDataURL();
      while (quality > 0 && (compressedDataUrl.length / 1024) > 100) {
        compressedDataUrl = canvas.toDataURL('image/jpeg', quality);
        quality -= 0.1;
      }
      const byteString = atob(compressedDataUrl.split(',')[1]);
      const mimeString = compressedDataUrl.split(',')[0].split(':')[1].split(';')[0];
      const ab = new ArrayBuffer(byteString.length);
      const ia = new Uint8Array(ab);
      for (let i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
      }
      const blob = new Blob([ab], { type: mimeString });
      resolve(new File([blob], file.name));
    };
    image.onerror = reject;
  });
}

export function setImageWithCompressionAndResize(images: FileList): Promise<{ file: File; src: string }> {
  return new Promise((resolve, reject) => {
    if (images) {
      compressImage(images[0]).then((compressedFile) => {
        convertFileToBase64(compressedFile, (imgBase64) => {
          resolve({ file: compressedFile, src: imgBase64 });
        });
      }).catch(reject);
    } else {
      reject(new Error('No images provided'));
    }
  });
}
