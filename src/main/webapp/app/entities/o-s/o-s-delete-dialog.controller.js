(function() {
    'use strict';

    angular
        .module('dockerTestApp')
        .controller('OSDeleteController',OSDeleteController);

    OSDeleteController.$inject = ['$uibModalInstance', 'entity', 'OS'];

    function OSDeleteController($uibModalInstance, entity, OS) {
        var vm = this;

        vm.oS = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OS.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
