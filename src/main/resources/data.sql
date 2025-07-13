-- Roller (sabit)
INSERT INTO rol (id, isim) VALUES (1, 'Yönetici') ON CONFLICT DO NOTHING;
INSERT INTO rol (id, isim) VALUES (2, 'Çalışan') ON CONFLICT DO NOTHING;

-- Örnek yönetici kullanıcı (şifresi: 1234 - encode edilmemişse backend login hatası verebilir)
INSERT INTO kullanici (id, isim, eposta, sifre, rol_id)
VALUES (1, 'Admin Kullanıcı', 'admin@example.com', '1234', 1)
    ON CONFLICT DO NOTHING;
