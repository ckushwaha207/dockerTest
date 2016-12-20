(function() {
    'use strict';

    angular
        .module('dockerTestApp')
        .controller('ToolDialogController', ToolDialogController);

    ToolDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tool', 'OS'];

    function ToolDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tool, OS) {
        var vm = this;

        vm.tool = entity;
        vm.clear = clear;
        vm.save = save;
        vm.os = OS.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tool.id !== null) {
                Tool.update(vm.tool, onSaveSuccess, onSaveError);
            } else {
                Tool.save(vm.tool, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('dockerTestApp:toolUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
