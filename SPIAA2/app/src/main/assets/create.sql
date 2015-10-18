CREATE TABLE usuario(
_id INTEGER NOT NULL,
  nome text NOT NULL,
  usuario text UNIQUE NOT NULL,
  email text NOT NULL,
  tipo text,
  numero text,
  turma text
);
CREATE TABLE bairro
(
 _id INTEGER NOT NULL,
 nome text,
 coordenadas text
);
CREATE TABLE denuncia
(
  _id INTEGER UNIQUE NOT NULL,
  endereco text,
  numero text,
  irregularidade text,
  observacao text,
  status text,
  data_abertura text,
  data_finalizacao text,
  bairro_fk INTEGER NOT NULL,
  usuario_fk INTEGER NOT NULL
);
CREATE TABLE quarteirao
(
  _id INTEGER NOT NULL,
  descricao text,
  bairro_fk INTEGER NOT NULL

);
CREATE TABLE criadouro(
_id INTEGER NOT NULL,
grupo text,
recipiente TEXT
);
CREATE TABLE tratamento_antivetorial(
_id INTEGER PRIMARY KEY NOT NULL,
data_boletim text NOT NULL,
numero text,
semana_epidemiologica text,
numero_atividade text,
tipo_atividade text,
turma text,
status text,
usuario_fk INTEGER NOT NULL,
bairro_fk INTEGER NOT NULL
);
CREATE TABLE tipo_imovel(
_id INTEGER NOT NULL,
sigla text,
descricao text
);
CREATE TABLE inseticida(
 _id INTEGER NOT NULL,
 nome text,
 unidade text
);
CREATE TABLE atividade(
 _id INTEGER PRIMARY KEY NOT NULL,
 endereco text,
 numero text,
 observacao text,
 inspecionado INTEGER,
 latitude text,
 longitude text,
 tipo_imovel_fk INTEGER NOT NULL,
 tratamento_antivetorial_fk INTEGER NOT NULL,
 quarteirao_fk INTEGER NOT NULL
);
CREATE TABLE atividade_criadouro(
atividade_fk INTEGER NOT NULL,
criadouro_fk INTEGER NOT NULL,
quantidade INTEGER
);
CREATE TABLE atividade_inseticida(
atividade_fk INTEGER NOT NULL,
inseticida_fk INTEGER NOT NULL,
quantidade INTEGER
);

