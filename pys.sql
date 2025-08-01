PGDMP  #    	                }            pys    17.5    17.5 4    W           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            X           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            Y           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            Z           1262    16583    pys    DATABASE     ~   CREATE DATABASE pys WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE pys;
                     postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                     pg_database_owner    false            [           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                        pg_database_owner    false    4            �            1255    16658    set_default_rol_id()    FUNCTION     �   CREATE FUNCTION public.set_default_rol_id() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  IF NEW.rol_id IS NULL THEN
    SELECT rol_id INTO NEW.rol_id
    FROM kullanicilar
    WHERE id = NEW.kullanici_id;
  END IF;
  RETURN NEW;
END;
$$;
 +   DROP FUNCTION public.set_default_rol_id();
       public               postgres    false    4            �            1259    16603    kullanicilar    TABLE     �   CREATE TABLE public.kullanicilar (
    id integer NOT NULL,
    isim character varying(100) NOT NULL,
    eposta character varying(100) NOT NULL,
    sifre text NOT NULL,
    rol_id integer NOT NULL
);
     DROP TABLE public.kullanicilar;
       public         heap r       postgres    false    4            �            1259    16602    kullanicilar_id_seq    SEQUENCE     �   CREATE SEQUENCE public.kullanicilar_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.kullanicilar_id_seq;
       public               postgres    false    222    4            \           0    0    kullanicilar_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.kullanicilar_id_seq OWNED BY public.kullanicilar.id;
          public               postgres    false    221            �            1259    16594    proje_durumlari    TABLE     j   CREATE TABLE public.proje_durumlari (
    id integer NOT NULL,
    isim character varying(50) NOT NULL
);
 #   DROP TABLE public.proje_durumlari;
       public         heap r       postgres    false    4            �            1259    16593    proje_durumlari_id_seq    SEQUENCE     �   CREATE SEQUENCE public.proje_durumlari_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.proje_durumlari_id_seq;
       public               postgres    false    220    4            ]           0    0    proje_durumlari_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.proje_durumlari_id_seq OWNED BY public.proje_durumlari.id;
          public               postgres    false    219            �            1259    16634    proje_kullanicilari    TABLE     �   CREATE TABLE public.proje_kullanicilari (
    id integer NOT NULL,
    proje_id integer NOT NULL,
    kullanici_id integer NOT NULL,
    rol_id integer,
    atanma_tarihi timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
 '   DROP TABLE public.proje_kullanicilari;
       public         heap r       postgres    false    4            �            1259    16633    proje_kullanicilari_id_seq    SEQUENCE     �   CREATE SEQUENCE public.proje_kullanicilari_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public.proje_kullanicilari_id_seq;
       public               postgres    false    4    226            ^           0    0    proje_kullanicilari_id_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE public.proje_kullanicilari_id_seq OWNED BY public.proje_kullanicilari.id;
          public               postgres    false    225            �            1259    16619    projeler    TABLE     �   CREATE TABLE public.projeler (
    id integer NOT NULL,
    isim character varying(100) NOT NULL,
    aciklama text,
    durum_id integer,
    olusturma_tarihi timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
    DROP TABLE public.projeler;
       public         heap r       postgres    false    4            �            1259    16618    projeler_id_seq    SEQUENCE     �   CREATE SEQUENCE public.projeler_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.projeler_id_seq;
       public               postgres    false    224    4            _           0    0    projeler_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.projeler_id_seq OWNED BY public.projeler.id;
          public               postgres    false    223            �            1259    16585    roller    TABLE     a   CREATE TABLE public.roller (
    id integer NOT NULL,
    isim character varying(50) NOT NULL
);
    DROP TABLE public.roller;
       public         heap r       postgres    false    4            �            1259    16584    roller_id_seq    SEQUENCE     �   CREATE SEQUENCE public.roller_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.roller_id_seq;
       public               postgres    false    218    4            `           0    0    roller_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.roller_id_seq OWNED BY public.roller.id;
          public               postgres    false    217            �           2604    16606    kullanicilar id    DEFAULT     r   ALTER TABLE ONLY public.kullanicilar ALTER COLUMN id SET DEFAULT nextval('public.kullanicilar_id_seq'::regclass);
 >   ALTER TABLE public.kullanicilar ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    222    221    222            �           2604    16597    proje_durumlari id    DEFAULT     x   ALTER TABLE ONLY public.proje_durumlari ALTER COLUMN id SET DEFAULT nextval('public.proje_durumlari_id_seq'::regclass);
 A   ALTER TABLE public.proje_durumlari ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    219    220    220            �           2604    16637    proje_kullanicilari id    DEFAULT     �   ALTER TABLE ONLY public.proje_kullanicilari ALTER COLUMN id SET DEFAULT nextval('public.proje_kullanicilari_id_seq'::regclass);
 E   ALTER TABLE public.proje_kullanicilari ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    225    226    226            �           2604    16622    projeler id    DEFAULT     j   ALTER TABLE ONLY public.projeler ALTER COLUMN id SET DEFAULT nextval('public.projeler_id_seq'::regclass);
 :   ALTER TABLE public.projeler ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    223    224    224            �           2604    16588 	   roller id    DEFAULT     f   ALTER TABLE ONLY public.roller ALTER COLUMN id SET DEFAULT nextval('public.roller_id_seq'::regclass);
 8   ALTER TABLE public.roller ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    217    218    218            P          0    16603    kullanicilar 
   TABLE DATA           G   COPY public.kullanicilar (id, isim, eposta, sifre, rol_id) FROM stdin;
    public               postgres    false    222   �>       N          0    16594    proje_durumlari 
   TABLE DATA           3   COPY public.proje_durumlari (id, isim) FROM stdin;
    public               postgres    false    220    ?       T          0    16634    proje_kullanicilari 
   TABLE DATA           `   COPY public.proje_kullanicilari (id, proje_id, kullanici_id, rol_id, atanma_tarihi) FROM stdin;
    public               postgres    false    226   a?       R          0    16619    projeler 
   TABLE DATA           R   COPY public.projeler (id, isim, aciklama, durum_id, olusturma_tarihi) FROM stdin;
    public               postgres    false    224   �?       L          0    16585    roller 
   TABLE DATA           *   COPY public.roller (id, isim) FROM stdin;
    public               postgres    false    218   i@       a           0    0    kullanicilar_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.kullanicilar_id_seq', 6, true);
          public               postgres    false    221            b           0    0    proje_durumlari_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.proje_durumlari_id_seq', 3, true);
          public               postgres    false    219            c           0    0    proje_kullanicilari_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.proje_kullanicilari_id_seq', 2, true);
          public               postgres    false    225            d           0    0    projeler_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.projeler_id_seq', 6, true);
          public               postgres    false    223            e           0    0    roller_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.roller_id_seq', 3, true);
          public               postgres    false    217            �           2606    16612 $   kullanicilar kullanicilar_eposta_key 
   CONSTRAINT     a   ALTER TABLE ONLY public.kullanicilar
    ADD CONSTRAINT kullanicilar_eposta_key UNIQUE (eposta);
 N   ALTER TABLE ONLY public.kullanicilar DROP CONSTRAINT kullanicilar_eposta_key;
       public                 postgres    false    222            �           2606    16610    kullanicilar kullanicilar_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.kullanicilar
    ADD CONSTRAINT kullanicilar_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.kullanicilar DROP CONSTRAINT kullanicilar_pkey;
       public                 postgres    false    222            �           2606    16601 (   proje_durumlari proje_durumlari_isim_key 
   CONSTRAINT     c   ALTER TABLE ONLY public.proje_durumlari
    ADD CONSTRAINT proje_durumlari_isim_key UNIQUE (isim);
 R   ALTER TABLE ONLY public.proje_durumlari DROP CONSTRAINT proje_durumlari_isim_key;
       public                 postgres    false    220            �           2606    16599 $   proje_durumlari proje_durumlari_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.proje_durumlari
    ADD CONSTRAINT proje_durumlari_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.proje_durumlari DROP CONSTRAINT proje_durumlari_pkey;
       public                 postgres    false    220            �           2606    16640 ,   proje_kullanicilari proje_kullanicilari_pkey 
   CONSTRAINT     j   ALTER TABLE ONLY public.proje_kullanicilari
    ADD CONSTRAINT proje_kullanicilari_pkey PRIMARY KEY (id);
 V   ALTER TABLE ONLY public.proje_kullanicilari DROP CONSTRAINT proje_kullanicilari_pkey;
       public                 postgres    false    226            �           2606    16642 A   proje_kullanicilari proje_kullanicilari_proje_id_kullanici_id_key 
   CONSTRAINT     �   ALTER TABLE ONLY public.proje_kullanicilari
    ADD CONSTRAINT proje_kullanicilari_proje_id_kullanici_id_key UNIQUE (proje_id, kullanici_id);
 k   ALTER TABLE ONLY public.proje_kullanicilari DROP CONSTRAINT proje_kullanicilari_proje_id_kullanici_id_key;
       public                 postgres    false    226    226            �           2606    16627    projeler projeler_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.projeler
    ADD CONSTRAINT projeler_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.projeler DROP CONSTRAINT projeler_pkey;
       public                 postgres    false    224            �           2606    16592    roller roller_isim_key 
   CONSTRAINT     Q   ALTER TABLE ONLY public.roller
    ADD CONSTRAINT roller_isim_key UNIQUE (isim);
 @   ALTER TABLE ONLY public.roller DROP CONSTRAINT roller_isim_key;
       public                 postgres    false    218            �           2606    16590    roller roller_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.roller
    ADD CONSTRAINT roller_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.roller DROP CONSTRAINT roller_pkey;
       public                 postgres    false    218            �           2620    16659 "   proje_kullanicilari trg_set_rol_id    TRIGGER     �   CREATE TRIGGER trg_set_rol_id BEFORE INSERT ON public.proje_kullanicilari FOR EACH ROW EXECUTE FUNCTION public.set_default_rol_id();
 ;   DROP TRIGGER trg_set_rol_id ON public.proje_kullanicilari;
       public               postgres    false    226    227            �           2606    16613 %   kullanicilar kullanicilar_rol_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.kullanicilar
    ADD CONSTRAINT kullanicilar_rol_id_fkey FOREIGN KEY (rol_id) REFERENCES public.roller(id) ON DELETE RESTRICT;
 O   ALTER TABLE ONLY public.kullanicilar DROP CONSTRAINT kullanicilar_rol_id_fkey;
       public               postgres    false    222    218    4773            �           2606    16648 9   proje_kullanicilari proje_kullanicilari_kullanici_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.proje_kullanicilari
    ADD CONSTRAINT proje_kullanicilari_kullanici_id_fkey FOREIGN KEY (kullanici_id) REFERENCES public.kullanicilar(id) ON DELETE CASCADE;
 c   ALTER TABLE ONLY public.proje_kullanicilari DROP CONSTRAINT proje_kullanicilari_kullanici_id_fkey;
       public               postgres    false    222    226    4781            �           2606    16643 5   proje_kullanicilari proje_kullanicilari_proje_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.proje_kullanicilari
    ADD CONSTRAINT proje_kullanicilari_proje_id_fkey FOREIGN KEY (proje_id) REFERENCES public.projeler(id) ON DELETE CASCADE;
 _   ALTER TABLE ONLY public.proje_kullanicilari DROP CONSTRAINT proje_kullanicilari_proje_id_fkey;
       public               postgres    false    224    226    4783            �           2606    16653 3   proje_kullanicilari proje_kullanicilari_rol_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.proje_kullanicilari
    ADD CONSTRAINT proje_kullanicilari_rol_id_fkey FOREIGN KEY (rol_id) REFERENCES public.roller(id) ON DELETE RESTRICT;
 ]   ALTER TABLE ONLY public.proje_kullanicilari DROP CONSTRAINT proje_kullanicilari_rol_id_fkey;
       public               postgres    false    218    4773    226            �           2606    16628    projeler projeler_durum_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.projeler
    ADD CONSTRAINT projeler_durum_id_fkey FOREIGN KEY (durum_id) REFERENCES public.proje_durumlari(id) ON DELETE SET NULL;
 I   ALTER TABLE ONLY public.projeler DROP CONSTRAINT projeler_durum_id_fkey;
       public               postgres    false    224    220    4777            P   }   x�3�t��M-�L����9�z�������)������E)��\F��)��y�� E� s���( �*
 +1�L��T�.��I�;�1��F�J��^6X 39�\C#cS3��=... \�7�      N   1   x�3�L���2�tI-K�UpMɬ�/�2�I�M��I�K9��+F��� #�      T   ,   x�3�4C##S]s]##C+S+=3ss�=... �w�      R   �   x��αA��z�)�l�fg��N!:4�b������P�=ދ��4�W���0����C�.��\Wkk-�ľG�G�\$/I��ؗ�h6Ź6m�ǹ�ǹպ��8i��-���;<�b�6��WuH/5$-��b�����?��M��n6�߃��Kx$J��%�B��!"�+�쒣��r$�fe�1O��K�      L   '   x�3�<�-/�$39�ˈ�p{bΑ�G�'�q��qqq �}?     