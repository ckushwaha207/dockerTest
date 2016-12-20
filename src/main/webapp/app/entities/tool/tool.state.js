(function() {
    'use strict';

    angular
        .module('dockerTestApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tool', {
            parent: 'entity',
            url: '/tool',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dockerTestApp.tool.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tool/tools.html',
                    controller: 'ToolController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tool');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tool-detail', {
            parent: 'entity',
            url: '/tool/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dockerTestApp.tool.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tool/tool-detail.html',
                    controller: 'ToolDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tool');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tool', function($stateParams, Tool) {
                    return Tool.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tool',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tool-detail.edit', {
            parent: 'tool-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tool/tool-dialog.html',
                    controller: 'ToolDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tool', function(Tool) {
                            return Tool.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tool.new', {
            parent: 'tool',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tool/tool-dialog.html',
                    controller: 'ToolDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                version: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tool', null, { reload: 'tool' });
                }, function() {
                    $state.go('tool');
                });
            }]
        })
        .state('tool.edit', {
            parent: 'tool',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tool/tool-dialog.html',
                    controller: 'ToolDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tool', function(Tool) {
                            return Tool.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tool', null, { reload: 'tool' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tool.delete', {
            parent: 'tool',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tool/tool-delete-dialog.html',
                    controller: 'ToolDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tool', function(Tool) {
                            return Tool.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tool', null, { reload: 'tool' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
