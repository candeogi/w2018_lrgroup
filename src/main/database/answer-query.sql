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

-- Query for the homepage
--
CREATE VIEW QuestionWithVotes AS
SELECT q.id as viewQuestionId, q.title as viewQuestionTitle, sum(v.vote) AS viewQuestionVotes
FROM question AS q JOIN votequestion AS v ON q.id = v.question
GROUP BY q.id;

SELECT qv.viewQuestionId as questionId, qv.viewQuestionTitle as questionTitle, COUNT(qv.viewQuestionId) as numberOfAnswers
FROM QuestionWithVotes AS qv JOIN have AS h ON qv.viewQuestionId = h.idquestion
GROUP BY qv.viewQuestionId,qv.viewQuestionTitle
ORDER BY numberOfAnswers;