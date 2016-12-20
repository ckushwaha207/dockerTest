(function() {
    'use strict';

    angular
        .module('dockerTestApp')
        .controller('OSDetailController', OSDetailController);

    OSDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OS', 'Tool'];

    function OSDetailController($scope, $rootScope, $stateParams, previousState, entity, OS, Tool) {
        var vm = this;

        vm.oS = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('dockerTestApp:oSUpdate', function(event, result) {
            vm.oS = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
