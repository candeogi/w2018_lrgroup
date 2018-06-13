(function() {

    var httpRequest;

    document.getElementById('categoriesList').addEventListener('submit', function(event) {


        httpRequest = new XMLHttpRequest();

        if (!httpRequest) {
            alert('Giving up :( Cannot create an XMLHttpRequest instance');
            return false;
        }

        httpRequest.onreadystatechange = writeResults;
        httpRequest.open('GET', '/categories-name');
        httpRequest.send();

        // stop the form from submitting the normal way and refreshing the page
        event.preventDefault();
    });

    function writeResults() {

        if (httpRequest.readyState === XMLHttpRequest.DONE) {

            // get the div where to write results
            var div = document.getElementById('categoriesResults');

            // clean it up
            div.className = '';
            while (div.firstChild) {
                div.removeChild(div.firstChild);
            }

            // generic DOM elements
            var e;
            var ee;

            // if we get a 500 status write the error message parsing it from JSON
            if (httpRequest.status == 500) {

                div.className = 'alert alert-danger';

                // the JSON error message
                var jsonData = JSON.parse(httpRequest.responseText);
                jsonData = jsonData['message'];

                e = document.createElement('ul');
                div.appendChild(e);

                ee = document.createElement('li');
                ee.appendChild(document.createTextNode('Message: ' + jsonData['message']));
                e.appendChild(ee);

                ee = document.createElement('li');
                ee.appendChild(document.createTextNode('Error code: ' + jsonData['error-code']));
                e.appendChild(ee);

                ee = document.createElement('li');
                ee.appendChild(document.createTextNode('Error details: ' + jsonData['error-details']));
                e.appendChild(ee);

                // if we get a 200 status write result table parsing it from JSON
            } else if (httpRequest.status == 200) {

                // a generic row and column of the table
                var tr;
                var td;

                // the JSON list of products
                var jsonData = JSON.parse(httpRequest.responseText);
                jsonData = jsonData['resource-list'];

                var category;

                e = document.createElement('table');
                e.className = 'table';
                div.appendChild(e);

                ee = document.createElement('thead');
                ee.className = 'thead-light';
                e.appendChild(ee);

                tr = document.createElement('tr');
                ee.appendChild(tr);

                td = document.createElement('th');
                td.appendChild(document.createTextNode());
                tr.appendChild(td);

                td = document.createElement('th');
                td.appendChild(document.createTextNode());
                tr.appendChild(td);

                td = document.createElement('th');
                td.appendChild(document.createTextNode('Question title'));
                tr.appendChild(td);

                td = document.createElement('th');
                td.appendChild(document.createTextNode('Author'));
                tr.appendChild(td);

                td = document.createElement('th');
                td.appendChild(document.createTextNode('Last Modified'));
                tr.appendChild(td);


                ee = document.createElement('tbody');
                e.appendChild(ee);

                for (var i = 0; i < jsonData.length; i++) {

                    category = jsonData[i].product;

                    tr = document.createElement('tr');
                    ee.appendChild(tr);

                    td = document.createElement('td');
                    td.appendChild(document.createTextNode(category['name']));
                    tr.appendChild(td);
                }

                // otherwise write a generic error message
            } else {
                div.className = 'alert alert-danger';

                e = document.createElement('p');
                e.appendChild(document.createTextNode('Unexpected error'));
                div.appendChild(e);
            }

        }
    }
})();