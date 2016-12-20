(function() {
    'use strict';

    angular
        .module('dockerTestApp')
        .controller('OSDialogController', OSDialogController);

    OSDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'OS', 'Tool'];

    function OSDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, OS, Tool) {
        var vm = this;

        vm.oS = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tools = Tool.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.oS.id !== null) {
                OS.update(vm.oS, onSaveSuccess, onSaveError);
            } else {
                OS.save(vm.oS, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('dockerTestApp:oSUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
