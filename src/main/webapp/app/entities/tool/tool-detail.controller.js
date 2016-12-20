(function() {
    'use strict';

    angular
        .module('dockerTestApp')
        .controller('ToolDetailController', ToolDetailController);

    ToolDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tool', 'OS'];

    function ToolDetailController($scope, $rootScope, $stateParams, previousState, entity, Tool, OS) {
        var vm = this;

        vm.tool = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('dockerTestApp:toolUpdate', function(event, result) {
            vm.tool = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
