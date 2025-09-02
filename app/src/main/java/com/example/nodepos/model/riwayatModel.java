package com.example.nodepos.model;

public class riwayatModel {
    private String invoice;
    private String status;
    private String date;
    private String total;



    public riwayatModel(String invoice, String status, String date, String total) {
        this.invoice = invoice;
        this.status = status;
        this.date = date;
        this.total = total;
    }


    public String getInvoice() {
        return invoice;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getTotal() {
        return total;
    }
}
