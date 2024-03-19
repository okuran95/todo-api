package com.todo.api.constant;

public class Constants {
    public static final String NO_AUTH = "Giriş yapmanız lazım.";
    public static final String INVALID_USERNAME_OR_PASSWORD = "Kullanıcı Adı veya Şifre hatalı.";
    public static final String TODO_NOT_FOUND = "Yapılacak etkinlik bulunamadı.";
    public static final String EMAIL_CAN_NOT_BE_NULL = "Email değeri boş olamaz.";
    public static final String USER_NOT_FOUND = "Kullanıcı bulunamadı.";
    public static final String PASSWORDS_ARE_NOT_EQUAL = "Şifreler eşleşmiyor.";
    public static final String EMAIL_ALREADY_EXIST = "Email zaten kayıtlı.";
    public static final String MESSAGE_DELETED = "Başarıyla silindi.";
    public static final String MESSAGE_LOGIN = "Başarıyla giriş yapıldı.";
    public static final String MESSAGE_CREATED = "Başarıyla oluşturuldu.";
    public static final String MESSAGE_FOUND = "Kayıt bulundu.";
    public static final String MESSAGE_UPDATED = "Başarıyla güncellendi";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_HEADER = "Authorization";
    public static final Integer VALIDITY_DURATION = 60 * 60 * 1000;
    public static final String START_DATE_TIME_CAN_NOT_BEFORE_NOW = "Geçmiş zaman için kayıt oluşturulamaz.";
}
