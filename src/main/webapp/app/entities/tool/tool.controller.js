(function() {
    'use strict';

    angular
        .module('dockerTestApp')
        .controller('ToolController', ToolController);

    ToolController.$inject = ['$scope', '$state', 'Tool'];

    function ToolController ($scope, $state, Tool) {
        var vm = this;

        vm.tools = [];

        loadAll();

        function loadAll() {
            Tool.query(function(result) {
                vm.tools = result;
                vm.searchQuery = null;
            });
        }
    }
})();
