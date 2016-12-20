(function() {
    'use strict';
    angular
        .module('dockerTestApp')
        .factory('Tool', Tool);

    Tool.$inject = ['$resource'];

    function Tool ($resource) {
        var resourceUrl =  'api/tools/:id';

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
