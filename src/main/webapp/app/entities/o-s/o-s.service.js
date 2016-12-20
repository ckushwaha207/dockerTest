(function() {
    'use strict';
    angular
        .module('dockerTestApp')
        .factory('OS', OS);

    OS.$inject = ['$resource'];

    function OS ($resource) {
        var resourceUrl =  'api/o-s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
