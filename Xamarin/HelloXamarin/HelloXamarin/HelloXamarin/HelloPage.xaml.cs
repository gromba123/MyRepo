using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace HelloXamarin
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class HelloPage : ContentPage
    {
        public HelloPage()
        {
            InitializeComponent();
            Label l = new Label
            {
                Text = "Xamarim with C#",
                FontSize = Device.GetNamedSize(NamedSize.Large, typeof(Label)),
            };
            Button b = new Button
            {
                Text = "Navigate!",
                HorizontalOptions = LayoutOptions.Center,
                VerticalOptions = LayoutOptions.Center
            };
            b.Clicked += (sender, args) => {
                l.Text = "Clicked";
            };
            
            Content = new ScrollView
            {
                Content = new StackLayout
                {
                    Children = {
                        l,
                        b
                    }
                }
            };
        }
    }
}