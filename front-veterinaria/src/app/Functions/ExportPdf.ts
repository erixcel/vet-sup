import * as printJS from "print-js"

export function exportToPdf( titulo:string, json:any[], cabecera:string[]){
    printJS({
        printable: json,
        type: 'json',
        properties: cabecera,
        header: titulo,
        documentTitle: 'Santos Cachorros',
        repeatTableHeader: true,
        style: `
        @media print {
        body {-webkit-print-color-adjust: exact;}
        }
        table {
        border-collapse: collapse; margin: 25px 0;
        font-size: 1em;
        font-family: sans-serif;
        min-width: 450px;
        box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
        }
        table thead tr {
        background-color: #466a98;
        color: #ffffff;
        text-align: middle;
        }
        table th, table td {
        padding: 12px 15px;
        }
        table tbody tr {
        border-bottom: 1px solid #dddddd;
        }
        table tbody tr:nth-of-type(even) {
        background-color: #f3f3f3;
        }
        `
        })
}

