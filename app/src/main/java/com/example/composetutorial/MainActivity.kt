package com.example.composetutorial

import android.content.res.Configuration
import android.os.Bundle
import android.provider.Telephony
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.stylusHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme{
                Conversation(SampleData.conversationSample)
                Surface(modifier = Modifier.fillMaxSize()) {
                    MessageCard(Message("Android", "Jetpack Compose"))
                }
            }
        }
    }
}
data class Message(val author: String, val body: String)
@Composable
fun MessageCard(msg: Message){
    //Adiciona preenchimento ao redor da nossa imagem
    Row(modifier = Modifier.padding(all= 8.dp)){
        Image(
            painter = painterResource(R.drawable.avatar_gamer),
            contentDescription = null,
            modifier = Modifier
            //Define o tamanho da imagem para:
                .size(40.dp)
            //Recorta a imagem para ter o formato de um círculo
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        //Adiciona um espaço horizontal entre a imagem e a linha da coluna
        Spacer(modifier = Modifier.width(8.dp))

        //Mantemos o controle se a mensagem foi expandida ou não neste contexto.
        var isExpanded by remember { mutableStateOf(false) }
        ///surfaceColor será atualizado gradualmente de uma cor para outra
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        )

    //Alternamos a variável isExpanded quando clicamos nesta coluna.
    Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
        Text(
            text= msg.author,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleSmall
        )
        //Adiciona um espaço vertical entre o autor e o texto da mensagem
        Spacer(modifier = Modifier.height(4.dp))

        Surface(
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 1.dp,
            //A cor mudará gradualmente de primária para surface
            color = surfaceColor,
            //Mudará o tamanho da sufarce gradualmente
            modifier = Modifier.animateContentSize().padding(1.dp)
        ) {
        Text(text = msg.body,
            modifier = Modifier.padding(all = 4.dp),
            //Se a mensagem estiver expandida, exibimos todo o seu conteúdo,
            //Caso contrário, exibimos apenas a primeira linha.
            maxLines = if (isExpanded) Int.MAX_VALUE else 1,
            style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn{
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)

@Composable
fun PreviewMessageCard() {
    ComposeTutorialTheme{
        Surface {
            MessageCard(
                msg = Message("Lexi", "Hey, take a look at Jetpack Compose, it's great!")
            )
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    ComposeTutorialTheme {
        Conversation(SampleData.conversationSample)
    }
}

