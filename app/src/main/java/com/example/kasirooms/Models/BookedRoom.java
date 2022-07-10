package com.example.kasirooms.Models;

public class BookedRoom {
    private String userId,roomId,status,name_surname,contact, email, address, town,section,deposit,date,type;

    public BookedRoom() {
    }

    public BookedRoom(String userId, String roomId, String status, String name_surname, String contact, String email, String address, String town, String section, String deposit, String date, String type) {
        this.userId = userId;
        this.roomId = roomId;
        this.status = status;
        this.name_surname = name_surname;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.town = town;
        this.section = section;
        this.deposit = deposit;
        this.date = date;
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName_surname() {
        return name_surname;
    }

    public void setName_surname(String name_surname) {
        this.name_surname = name_surname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
