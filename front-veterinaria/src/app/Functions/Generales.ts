export function rangoDelMesActual(date: Date) {
  const currentMonth = date.getMonth()
  const currentYear = date.getFullYear()
  const inicio = new Date(currentYear, currentMonth, 1)
  const final = new Date(currentYear, currentMonth + 1, 1)
  return {inicio, final}
}

export function rangoDelDiaActual(date: Date) {
  const inicio = new Date(date.getFullYear(), date.getMonth(), date.getDate(), 0, 0, 0, 0)
  const final = new Date(date.getFullYear(), date.getMonth(), date.getDate(), 23, 59, 59, 999)
  return {inicio, final}
}

export function getCurrentDate(date: Date): string {
  const year = date.getFullYear()
  const month = (date.getMonth() + 1).toString().padStart(2, '0')
  const day = date.getDate().toString().padStart(2, '0')
  return `${year}-${month}-${day}`
}

export function getDateNow(): string {
  const date = new Date();
  const day = date.getDate();
  const month = date.getMonth() + 1;
  const year = date.getFullYear();
  return `${day}/${month}/${year}`;
}

export function formatNumber(num: number): string {
  let formattedNumber = num.toString();
  while (formattedNumber.length < 7) {
    formattedNumber = '0' + formattedNumber;
  }
  return formattedNumber;
}

