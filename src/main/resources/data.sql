
INSERT INTO roller (id, isim) VALUES (1, 'Yönetici') ON CONFLICT DO NOTHING;
INSERT INTO roller (id, isim) VALUES (2, 'Çalışan') ON CONFLICT DO NOTHING;

INSERT INTO proje_durumlari (id, isim) VALUES (1, 'Planlandı') ON CONFLICT DO NOTHING;
INSERT INTO proje_durumlari (id, isim) VALUES (2, 'Devam Ediyor') ON CONFLICT DO NOTHING;
INSERT INTO proje_durumlari (id, isim) VALUES (3, 'Tamamlandı') ON CONFLICT DO NOTHING;

INSERT INTO kullanicilar (id, isim, eposta, sifre, rol_id)
VALUES (1, 'Admin Kullanıcı', 'admin@example.com', '1234', 1)
    ON CONFLICT DO NOTHING;

INSERT INTO kullanicilar (id, isim, eposta, sifre, rol_id)
VALUES (2, 'Çalışan Ahmet', 'ahmet@example.com', '1234', 2)
    ON CONFLICT DO NOTHING;

INSERT INTO projeler (id, isim, aciklama, durum_id)
VALUES (1, 'İlk Proje', 'Bu bir test projesidir.', 1)
    ON CONFLICT DO NOTHING;

INSERT INTO proje_kullanicilari (proje_id, kullanici_id)
VALUES (1, 2)
    ON CONFLICT DO NOTHING;
