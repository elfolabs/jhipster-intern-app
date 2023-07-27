import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'media-file',
        data: { pageTitle: 'MediaFiles' },
        loadChildren: () => import('./media-file/media-file.module').then(m => m.MediaFileModule),
      },
      {
        path: 'pessoa',
        data: { pageTitle: 'Pessoas' },
        loadChildren: () => import('./pessoa/pessoa.module').then(m => m.PessoaModule),
      },
      {
        path: 'pessoa-contato',
        data: { pageTitle: 'PessoaContatoes' },
        loadChildren: () => import('./pessoa-contato/pessoa-contato.module').then(m => m.PessoaContatoModule),
      },
      {
        path: 'pessoa-rede-social',
        data: { pageTitle: 'PessoaRedeSocials' },
        loadChildren: () => import('./pessoa-rede-social/pessoa-rede-social.module').then(m => m.PessoaRedeSocialModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
