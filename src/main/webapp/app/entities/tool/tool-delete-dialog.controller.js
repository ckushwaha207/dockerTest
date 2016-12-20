(function() {
    'use strict';

    angular
        .module('dockerTestApp')
        .controller('ToolDeleteController',ToolDeleteController);

    ToolDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tool'];

    function ToolDeleteController($uibModalInstance, entity, Tool) {
        var vm = this;

        vm.tool = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tool.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
