(function() {
    'use strict';

    angular
        .module('dockerTestApp')
        .controller('OSController', OSController);

    OSController.$inject = ['$scope', '$state', 'OS'];

    function OSController ($scope, $state, OS) {
        var vm = this;

        vm.oS = [];

        loadAll();

        function loadAll() {
            OS.query(function(result) {
                vm.oS = result;
                vm.searchQuery = null;
            });
        }
    }
})();
