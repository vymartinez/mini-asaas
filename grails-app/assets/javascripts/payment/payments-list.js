(() => {
    'use strict';

    function debounce(fn, delay) {
        let timer = null;
        return function(...args) {
            clearTimeout(timer);
            timer = setTimeout(() => fn.apply(this, args), delay);
        };
    }

    const panel= document.querySelector('.js-payments-search');
    const searchUrl = document.querySelector('.js-search-url')?.value;
    const searchInp = panel?.querySelector('.js-search-input');
    const pagination= panel?.querySelector('.js-pagination');

    if (!panel || !searchUrl || !searchInp) {
        return;
    }

    async function fetchPayments() {
        try {
            const params = new URLSearchParams(window.location.search);
            const term = searchInp.value.trim();

            if (term) {
                params.set('payer.name[like]', term);
            } else {
                params.delete('payer.name[like]');
            }

            const url = `${searchUrl}?${params.toString()}`;

            const resp = await fetch(url, {
                headers: { 'X-Requested-With': 'XMLHttpRequest' }
            });

            if (!resp.ok) throw new Error(`HTTP ${resp.status}`);

            const html = await resp.text();
            const doc  = new DOMParser().parseFromString(html, 'text/html');
            const newPanel = doc.querySelector('.js-payments-search');

            if (newPanel) {
                panel.innerHTML = newPanel.innerHTML;
                document.dispatchEvent(new CustomEvent('AtlasContentLoaded'));
            }
        } catch (err) {
            console.error('Erro ao buscar cobranças:', err);
        }
    }

    const onInput = debounce(fetchPayments, 300);
    searchInp.addEventListener('input', onInput);

    searchInp.addEventListener('atlas-input-trigger-search', () => {
        const params = new URLSearchParams(window.location.search);
        const term = searchInp.value.trim();
        if (term) params.set('payer.name[like]', term);
        else params.delete('payer.name[like]');
        params.set('page', '1');
        window.history.pushState(null, '', `${searchUrl}?${params.toString()}`);
        fetchPayments();
    });

    panel.addEventListener('click', e => {
        const link = e.target.closest('.js-pagination a');
        if (!link) return;
        e.preventDefault();
        window.history.pushState(null, '', link.href);
        fetchPayments();
    });

    window.addEventListener('popstate', fetchPayments);
})();