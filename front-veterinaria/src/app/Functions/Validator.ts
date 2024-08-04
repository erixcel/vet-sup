import { AbstractControl, ValidationErrors, ValidatorFn, Validators } from '@angular/forms'

export function passwordValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {

    const value = control.value
    const errors: ValidationErrors = {}
    const hasUpperCase = /[A-Z]/.test(value)
    const hasSpecialChar = /[!@#$%^&*]/.test(value)
    const minLengthError = Validators.minLength(10)(control)
    const requiredError = Validators.required(control)

    if (requiredError) {
      errors['required'] = true
    }

    if (!value) {
      return Object.keys(errors).length > 0 ? errors : null
    } else if (!hasUpperCase) {
      errors['uppercase'] = true
    } else if (!hasSpecialChar) {
      errors['special-char'] = true
    } else if (minLengthError) {
      errors['minlength'] = minLengthError['minlength']
    }

    return Object.keys(errors).length > 0 ? errors : null
  }
}


