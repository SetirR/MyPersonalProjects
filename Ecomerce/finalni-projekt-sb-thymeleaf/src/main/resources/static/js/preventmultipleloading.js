$(function()
{
    $('form').each(function()
    {
        let form = $(this);
        // jak dát zdroj, IMG SRC nebo TH SRC ???
        let loading = $('img th:src="@{/img/loading.gif}').addclass('loading').hide();
        $().after(loading);
        
        form.submit(function(e)
        {
            if(form.data('submitted') === true)
            {
                e.preventDefault();
            }
            
            else
            {
                form.data('submitted', true);
                $(button, 'this').prop('disabled', true).removeClass('btn-primary').addClass('btn-secondary');
                loading.show();
            }
        });
    });
});


