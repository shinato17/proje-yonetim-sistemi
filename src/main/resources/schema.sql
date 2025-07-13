CREATE TABLE IF NOT EXISTS roller (
                                      id SERIAL PRIMARY KEY,
                                      isim VARCHAR(50) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS proje_durumlari (
                                               id SERIAL PRIMARY KEY,
                                               isim VARCHAR(50) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS kullanicilar (
                                            id SERIAL PRIMARY KEY,
                                            isim VARCHAR(100) NOT NULL,
    eposta VARCHAR(100) NOT NULL UNIQUE,
    sifre TEXT NOT NULL,
    rol_id INTEGER NOT NULL,
    FOREIGN KEY (rol_id) REFERENCES roller(id) ON DELETE RESTRICT
    );

CREATE TABLE IF NOT EXISTS projeler (
                                        id SERIAL PRIMARY KEY,
                                        isim VARCHAR(100) NOT NULL,
    aciklama TEXT,
    durum_id INTEGER,
    olusturma_tarihi TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (durum_id) REFERENCES proje_durumlari(id) ON DELETE SET NULL
    );

CREATE TABLE IF NOT EXISTS proje_kullanicilari (
                                                   id SERIAL PRIMARY KEY,
                                                   proje_id INTEGER NOT NULL,
                                                   kullanici_id INTEGER NOT NULL,
                                                   rol_id INTEGER,
                                                   atanma_tarihi TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                   FOREIGN KEY (proje_id) REFERENCES projeler(id) ON DELETE CASCADE,
    FOREIGN KEY (kullanici_id) REFERENCES kullanicilar(id) ON DELETE CASCADE,
    FOREIGN KEY (rol_id) REFERENCES roller(id) ON DELETE RESTRICT,
    UNIQUE (proje_id, kullanici_id)
    );

CREATE OR REPLACE FUNCTION set_default_rol_id()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.rol_id IS NULL THEN
SELECT rol_id INTO NEW.rol_id
FROM kullanicilar
WHERE id = NEW.kullanici_id;
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_set_rol_id
    BEFORE INSERT ON proje_kullanicilari
    FOR EACH ROW
    EXECUTE FUNCTION set_default_rol_id();
