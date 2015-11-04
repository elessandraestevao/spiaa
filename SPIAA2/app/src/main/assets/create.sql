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
 nome text NOT NULL,
 coordenadas text
);
CREATE TABLE denuncia
(
  _id INTEGER UNIQUE NOT NULL,
  endereco text NOT NULL,
  numero text NOT NULL,
  irregularidade text NOT NULL,
  observacao text,
  status text NOT NULL,
  data_abertura text,
  data_finalizacao text,
  bairro_fk INTEGER NOT NULL,
  usuario_fk INTEGER NOT NULL
);
CREATE TABLE quarteirao
(
  _id INTEGER NOT NULL,
  descricao text NOT NULL,
  bairro_fk INTEGER NOT NULL

);
CREATE TABLE criadouro(
_id INTEGER NOT NULL,
grupo text NOT NULL,
recipiente text
);
CREATE TABLE tratamento_antivetorial(
_id INTEGER PRIMARY KEY NOT NULL,
data_boletim text NOT NULL,
numero text,
semana_epidemiologica text NOT NULL,
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
descricao text NOT NULL
);
CREATE TABLE inseticida(
 _id INTEGER NOT NULL,
 nome text NOT NULL,
 unidade text
);
CREATE TABLE atividade(
 _id INTEGER PRIMARY KEY NOT NULL,
 endereco text NOT NULL,
 numero text NOT NULL,
 observacao text NOT NULL,
 inspecionado INTEGER,
 latitude text,
 longitude text,
 tipo_imovel_fk INTEGER NOT NULL,
 tratamento_antivetorial_fk INTEGER NOT NULL,
 quarteirao_fk INTEGER NOT NULL,
 data_inicial text NOT NULL,
 data_final text NOT NULL
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

