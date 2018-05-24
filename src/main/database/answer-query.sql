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

-- HOMEPAGE QUERY
-- Displays Questions as id and title sorted by number of votes.
-- Shows number of answers related to a single question aswell.
SELECT q.id as question_id, q.title as question_title, sum(v.vote) AS n_of_votes, count(q.id) as n_of_answers
FROM (lr_group.question AS q JOIN lr_group.votequestion AS v ON q.id = v.question) JOIN have AS h on q.id = h.idquestion
GROUP BY q.id
ORDER BY n_of_answers;

-- Display all answers related to a question
SELECT *
FROM lr_group.have AS h JOIN lr_group.answer AS a ON h.idanswer = a.id
WHERE h.idquestion= 1;