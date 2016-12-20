(function() {
    'use strict';

    angular
        .module('dockerTestApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('o-s', {
            parent: 'entity',
            url: '/o-s',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dockerTestApp.oS.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/o-s/o-s.html',
                    controller: 'OSController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('oS');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('o-s-detail', {
            parent: 'entity',
            url: '/o-s/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dockerTestApp.oS.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/o-s/o-s-detail.html',
                    controller: 'OSDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('oS');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'OS', function($stateParams, OS) {
                    return OS.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'o-s',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('o-s-detail.edit', {
            parent: 'o-s-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/o-s/o-s-dialog.html',
                    controller: 'OSDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OS', function(OS) {
                            return OS.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('o-s.new', {
            parent: 'o-s',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/o-s/o-s-dialog.html',
                    controller: 'OSDialogController',
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
                    $state.go('o-s', null, { reload: 'o-s' });
                }, function() {
                    $state.go('o-s');
                });
            }]
        })
        .state('o-s.edit', {
            parent: 'o-s',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/o-s/o-s-dialog.html',
                    controller: 'OSDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OS', function(OS) {
                            return OS.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('o-s', null, { reload: 'o-s' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('o-s.delete', {
            parent: 'o-s',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/o-s/o-s-delete-dialog.html',
                    controller: 'OSDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OS', function(OS) {
                            return OS.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('o-s', null, { reload: 'o-s' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
