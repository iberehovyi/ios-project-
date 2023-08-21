//
//  WelcomePageView.swift
//  IdealoLine
//
//  Created by Igor Berehovyi (RIT Student) on 12/3/22.
//

import SwiftUI
import Lottie

class ViewController: UIViewController {
    var animationView: LottieAnimationView?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        animationView = .init(name: "loading-welcome-page")
        animationView?.frame = view.bounds
        animationView?.loopMode = .loop
        animationView?.animationSpeed = 0.5
        view.addSubview(animationView!)
        animationView?.play()
    }
}

struct WelcomePageView: View {
    var body: some View {
        NavigationView {
            VStack {
                Text("Welcome")
                    .foregroundColor(.green)
                    .font(.largeTitle)
            
                Spacer()
                
                Image("logo")
                    .resizable()
                    .scaledToFit()
                    .frame(width: 300.0, height: 300.0)
                    .padding(.top, 50)
                
                Spacer()
                
                LolliView()
                    .frame(width: 150, height: 150)
                
                NavigationLink (destination: MainContentView()) {
                    Text("Go next")
                        .foregroundColor(.green)
                        .font(
                            .system(size: 20)
                            .weight(.heavy)
                        )
                }
            }
            .padding()
        }
    }
}

struct WelcomePageView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
