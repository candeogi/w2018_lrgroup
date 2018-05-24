--  Copyright 2018 University of Padua, Italy
--
--  Licensed under the Apache License, Version 2.0 (the "License");
--  you may not use this file except in compliance with the License.
--  You may obtain a copy of the License at
--
--      http://www.apache.org/licenses/LICENSE-2.0
--
--  Unless required by applicable law or agreed to in writing, software
--  distributed under the License is distributed on an "AS IS" BASIS,
--  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
--  See the License for the specific language governing permissions and
--  limitations under the License.
--
--  Author: Giovanni Candeo
--  Version: 1.0
--  Since: 1.0

--
-- Insertion into the Utente table
--

INSERT INTO lr_group.Utente VALUES ('admin@gmail.com', 'Admin', 'Powerful', 'admin', 'profilepath',
                          lr_group.CRYPT('admin', lr_group.gen_salt('bf',8) ), TRUE, '2017-1-1', '1994-1-1', 'randomtext');
INSERT INTO lr_group.Utente VALUES ('primoutente@gmail.com', 'primo', 'utente', 'primoutente', 'profilepath',
                          lr_group.CRYPT('passtest', lr_group.gen_salt('bf',8)  ), FALSE, '2017-1-1', '1994-1-1', 'randomtext');
INSERT INTO lr_group.Utente VALUES ('secondoutente@gmail.com', 'secondo', 'utente', 'secondoutente', 'profilepath',
                          lr_group.CRYPT('passtest', lr_group.gen_salt('bf',8)  ), FALSE, '2017-1-1', '1994-1-1', 'randomtext');
INSERT INTO lr_group.Utente VALUES ('terzoutente@gmail.com', 'terzo', 'utente', 'terzoutente', 'profilepath',
                          lr_group.CRYPT('passtest', lr_group.gen_salt('bf',8)  ), FALSE, '2017-1-1', '1994-1-1', 'randomtext');
INSERT INTO lr_group.Utente VALUES ('mauro@gmail.com', 'mauro', 'zoppo', 'maurozoppo', 'profilepath',
                          lr_group.CRYPT('passtest', lr_group.gen_salt('bf',8)  ), FALSE, '2017-1-1', '1994-1-1', 'randomtext');
INSERT INTO lr_group.Utente VALUES ('lucarossi@gmail.com', 'luca', 'rossi', 'lucarossi', 'profilepath',
                          lr_group.CRYPT('passtest', lr_group.gen_salt('bf',8)  ), FALSE, '2017-1-1', '1994-1-1', 'randomtext');
INSERT INTO lr_group.Utente VALUES ('ferronic@gmail.com', 'nicola', 'ferro', 'nicolaferro', 'profilepath',
                          lr_group.CRYPT('passtest', lr_group.gen_salt('bf',8)  ), FALSE, '2017-1-1', '1994-1-1', 'randomtext');


